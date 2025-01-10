package com.projects.paymentsworker.scheduler;

import dtos.Payment;
import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repository.InstantPaymentQueue;
import repository.PaymentJobRedisRepository;
import repository.PaymentsRedisRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentsExecutorService {

    private final PaymentJobRedisRepository paymentJobRedisRepository;
    private final PaymentsRedisRepository paymentsRedisRepository;
    private final InstantPaymentQueue instantPaymentQueue;
    private final PaymentProcessor paymentProcessor;

    public void processScheduledPayments() {
        List<PaymentJobUnit> paymentJobs = paymentJobRedisRepository.findAllByScheduledFor(LocalDate.now().toEpochDay());
        List<String> paymentsIds = paymentJobs.stream().map(PaymentJobUnit::getPaymentId).toList();

        HashSet<Payment> payments = new HashSet<>((Collection<Payment>) paymentsRedisRepository.findAllById(paymentsIds));
        payments.parallelStream()
                .forEach(paymentProcessor::processPayment);

        paymentsRedisRepository.saveAll(payments);
        paymentJobRedisRepository.deleteAll(paymentJobs);
    }

    public void processInstantPayments() {
        instantPaymentQueue.getQueue().parallelStream()
                .forEach(job -> {
            Optional<Payment> payment = paymentsRedisRepository.findById(job.getPaymentId());
            paymentProcessor.processPayment(payment.get());
            paymentsRedisRepository.save(payment.get());
            instantPaymentQueue.getQueue().remove(job);
        });
    }

}

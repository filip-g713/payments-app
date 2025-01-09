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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentsSchedulerService {

    private final PaymentJobRedisRepository paymentJobRedisRepository;
    private final PaymentsRedisRepository paymentsRedisRepository;
    private final InstantPaymentQueue instantPaymentQueue;

    public void processScheduledPayments() {
        List<PaymentJobUnit> incompletePaymentJobs = paymentJobRedisRepository.findAllByScheduledFor(LocalDate.now().toEpochDay());
        List<String> paymentsIds = incompletePaymentJobs.stream().map(PaymentJobUnit::getPaymentId).toList();
        Iterable<Payment> payments = paymentsRedisRepository.findAllById(paymentsIds);
        payments.forEach(this::completePaymentJob);
        paymentsRedisRepository.saveAll(payments);
        paymentJobRedisRepository.deleteAll(incompletePaymentJobs);
    }

    public void processInstantPayments() {
        PaymentJobUnit nextJob = instantPaymentQueue.getNext();
         while (Objects.nonNull(nextJob)) {
             Optional<Payment> payment = paymentsRedisRepository.findById(nextJob.getPaymentId());
             completePaymentJob(payment.get());
             paymentsRedisRepository.save(payment.get());
             nextJob = instantPaymentQueue.getNext();
         }
    }

    //mock "integration" for completing payments
    private void completePaymentJob(Payment payment) {
        log.info("Payment job completed: {}", payment);
        payment.setCompleted("true");
        payment.setSuccess("true");
    }
}

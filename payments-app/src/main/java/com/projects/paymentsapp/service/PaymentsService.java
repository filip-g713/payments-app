package com.projects.paymentsapp.service;


import dtos.Payment;
import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repository.PaymentsRedisRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentsService {

    private final PaymentsRedisRepository paymentsRedisRepository;
    private final PaymentsJobServiceFactory paymentsJobServiceFactory;
    private final AccountsService accountsService;

    public Payment getPayment(String id) {
        return paymentsRedisRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveScheduledPayment(Payment payment) {
        payment = createPayment(payment);
        freezePaymentAmount(payment);
        paymentsJobServiceFactory.getPaymentsJobService(ScheduledPaymentsJobService.BEAN_ID).createPaymentJob(payment);
    }

    public void saveInstantPayment(Payment payment) {
        payment.setScheduledFor(LocalDate.now().toEpochDay());
        payment = createPayment(payment);
        freezePaymentAmount(payment);
        paymentsJobServiceFactory.getPaymentsJobService(InstantPaymentsJobService.BEAN_ID).createPaymentJob(payment);
    }

    public void cancelPayment(String jobId) {
        PaymentJobUnit paymentJob = paymentsJobServiceFactory.getPaymentsJobService(ScheduledPaymentsJobService.BEAN_ID)
                .getPaymentJob(jobId);
        paymentsJobServiceFactory.getPaymentsJobService(ScheduledPaymentsJobService.BEAN_ID).cancelPaymentJob(jobId);
        paymentsRedisRepository.deleteById(paymentJob.getPaymentId());
    }
    private Payment createPayment(Payment payment) {
        log.info("Creating payment {}", payment);
        return paymentsRedisRepository.save(payment);
    }

    private void freezePaymentAmount(Payment payment) {
        accountsService.freezeAccountBalanceForPayment(payment);
    }

}

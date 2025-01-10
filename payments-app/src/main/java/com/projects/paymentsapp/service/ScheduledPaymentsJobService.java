package com.projects.paymentsapp.service;

import dtos.Payment;
import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repository.PaymentJobRedisRepository;

@Service(ScheduledPaymentsJobService.BEAN_ID)
@AllArgsConstructor
@Slf4j
public class ScheduledPaymentsJobService implements PaymentsJobService {

    public static final String BEAN_ID = "scheduledPaymentsJobService";

    private final PaymentJobRedisRepository paymentJobRedisRepository;

    @Override
    public void createPaymentJob(Payment payment) {
        PaymentJobUnit paymentJobUnit = PaymentJobUnit.builder()
                .paymentId(payment.getId())
                .scheduledFor(payment.getScheduledFor())
                .build();
        paymentJobRedisRepository.save(paymentJobUnit
        );
        log.info("Created scheduled Payment Job {} for payment {}",paymentJobUnit.getId(), payment.getId());
    }

    @Override
    public void cancelPaymentJob(String id) {
        paymentJobRedisRepository.deleteById(id);
    }

    @Override
    public PaymentJobUnit getPaymentJob(String id) {
       return paymentJobRedisRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment Job not found"));
    }
}

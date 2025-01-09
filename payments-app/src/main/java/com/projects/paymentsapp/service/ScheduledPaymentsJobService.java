package com.projects.paymentsapp.service;

import dtos.Payment;
import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.PaymentJobRedisRepository;

@Service(ScheduledPaymentsJobService.BEAN_ID)
@AllArgsConstructor
public class ScheduledPaymentsJobService implements PaymentsJobService {

    public static final String BEAN_ID = "scheduledPaymentsJobService";

    private final PaymentJobRedisRepository paymentJobRedisRepository;

    @Override
    public void createPaymentJob(Payment payment) {
        paymentJobRedisRepository.save(PaymentJobUnit.builder()
                .paymentId(payment.getId())
                .scheduledFor(payment.getScheduledFor())
                .build()
        );
    }
}

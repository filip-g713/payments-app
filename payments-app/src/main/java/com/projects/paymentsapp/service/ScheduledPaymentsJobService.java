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
        paymentJobRedisRepository.save(PaymentJobUnit.builder()
                .paymentId(payment.getId())
                .scheduledFor(payment.getScheduledFor())
                .build()
        );
        log.info("Created scheduled Payment Job for payment {}", payment.getId());
    }
}

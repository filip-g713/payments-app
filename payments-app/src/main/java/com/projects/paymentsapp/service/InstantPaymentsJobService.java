package com.projects.paymentsapp.service;

import dtos.Payment;
import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repository.InstantPaymentQueue;

@Service(InstantPaymentsJobService.BEAN_ID)
@AllArgsConstructor
@Slf4j
public class InstantPaymentsJobService implements PaymentsJobService {

    public static final String BEAN_ID = "instantPaymentsJobService";

    private final InstantPaymentQueue instantPaymentQueue;

    @Override
    public void createPaymentJob(Payment payment) {
        instantPaymentQueue.getQueue().offer(PaymentJobUnit.builder()
                .paymentId(payment.getId())
                .build()
        );
        log.info("Created instant payment job for payment {}", payment.getId());
        log.info("Queue size: {}", instantPaymentQueue.getQueue().size());
    }

    @Override
    public void cancelPaymentJob(String id) {
        log.error("Cancelling instant payment not available");
    }

    @Override
    public PaymentJobUnit getPaymentJob(String id) {
        log.error("Not implemented yet");
        return null;
    }
}

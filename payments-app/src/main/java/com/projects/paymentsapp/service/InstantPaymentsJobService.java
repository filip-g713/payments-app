package com.projects.paymentsapp.service;

import dtos.Payment;
import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.InstantPaymentQueue;

@Service(InstantPaymentsJobService.BEAN_ID)
@AllArgsConstructor
public class InstantPaymentsJobService implements PaymentsJobService {

    public static final String BEAN_ID = "instantPaymentsJobService";

    private final InstantPaymentQueue instantPaymentQueue;

    @Override
    public void createPaymentJob(Payment payment) {
        instantPaymentQueue.putNext(PaymentJobUnit.builder()
                .paymentId(payment.getId())
                .build()
        );
    }
}

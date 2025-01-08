package com.projects.paymentsapp.service;

import dtos.Payment;
import dtos.PaymentJobUnit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.InstantPaymentQueue;
import repository.PaymentJobRedisRepository;

@Service
@AllArgsConstructor
public class PaymentsJobService {
    private final PaymentJobRedisRepository paymentJobRedisRepository;
    private final InstantPaymentQueue instantPaymentQueue;

    public void schedulePaymentJob(Payment payment) {
        paymentJobRedisRepository.save(PaymentJobUnit.builder()
                .payment(payment)
                .completed("false")
                .success("false")
                .build()
        );
    }

    public void scheduleInstantPayment(Payment payment) {
        instantPaymentQueue.putNext(PaymentJobUnit.builder()
                .payment(payment)
                .completed("false")
                .success("false")
                .build()
        );
    }
}

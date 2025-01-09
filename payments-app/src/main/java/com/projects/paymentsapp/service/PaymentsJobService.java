package com.projects.paymentsapp.service;

import dtos.Payment;
import dtos.PaymentJobUnit;

public interface PaymentsJobService {
    void createPaymentJob(Payment payment);

    void cancelPaymentJob(String id);

    PaymentJobUnit getPaymentJob(String id);
}

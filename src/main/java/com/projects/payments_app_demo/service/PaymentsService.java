package com.projects.payments_app_demo.service;

import com.projects.payments_app_demo.dtos.Payment;
import com.projects.payments_app_demo.repository.PaymentsRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentsService {

    private final PaymentsRedisRepository paymentsRedisRepository;

    public Payment getPayment(String id) {
        return paymentsRedisRepository.getPaymentById(id);
    }

    public void savePayment(Payment payment) {
        paymentsRedisRepository.savePayment(payment);
    }
}

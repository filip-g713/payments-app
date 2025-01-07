package com.projects.payments_app_demo.service;

import com.projects.payments_app_demo.dtos.Payment;
import com.projects.payments_app_demo.repository.PaymentsRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentsService {

    private final PaymentsRedisRepository paymentsRedisRepository;

    public Payment getPayment(String id) {
        return paymentsRedisRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void savePayment(Payment payment) {
        paymentsRedisRepository.save(payment);
    }

    public List<Payment> getPayments() {
        return (List<Payment>) paymentsRedisRepository.findAll();
    }

    public void deletePaymentById(String id) {
        paymentsRedisRepository.deleteById(id);
    }

    public void deletePayments() {
        paymentsRedisRepository.deleteAll();
    }
}

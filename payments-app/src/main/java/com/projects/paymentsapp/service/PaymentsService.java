package com.projects.paymentsapp.service;


import dtos.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.PaymentsRedisRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentsService {

    private final PaymentsRedisRepository paymentsRedisRepository;
    private final PaymentsJobService paymentsJobService;

    public Payment getPayment(String id) {
        return paymentsRedisRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveAndSchedulePayment(Payment payment) {
        payment = paymentsRedisRepository.save(payment);
        if (payment.isInstant()) {
            paymentsJobService.scheduleInstantPayment(payment);
        } else {
            paymentsJobService.schedulePaymentJob(payment);
        }
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

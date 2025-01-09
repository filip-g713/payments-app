package com.projects.paymentsapp.service;


import dtos.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.PaymentsRedisRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class PaymentsService {

    private final PaymentsRedisRepository paymentsRedisRepository;
    private final PaymentsJobServiceFactory paymentsJobServiceFactory;

    public Payment getPayment(String id) {
        return paymentsRedisRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveScheduledPayment(Payment payment) {
        payment = paymentsRedisRepository.save(payment);
        paymentsJobServiceFactory.getPaymentsJobService(ScheduledPaymentsJobService.BEAN_ID).createPaymentJob(payment);
    }

    public void saveInstantPayment(Payment payment) {
        payment.setScheduledFor(LocalDate.now().toEpochDay());
        payment = paymentsRedisRepository.save(payment);
        paymentsJobServiceFactory.getPaymentsJobService(InstantPaymentsJobService.BEAN_ID).createPaymentJob(payment);
    }
}

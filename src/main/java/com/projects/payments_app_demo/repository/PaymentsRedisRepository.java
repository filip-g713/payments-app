package com.projects.payments_app_demo.repository;

import com.projects.payments_app_demo.dtos.Payment;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PaymentsRedisRepository {

    private final RedisTemplate<String, Payment> paymentRedisTemplate;

    public Payment getPaymentById(String id) {
        return paymentRedisTemplate.opsForValue().get(id);
    }

    public void savePayment(Payment payment) {
        paymentRedisTemplate.opsForValue().set(payment.getId(), payment);
    }

}

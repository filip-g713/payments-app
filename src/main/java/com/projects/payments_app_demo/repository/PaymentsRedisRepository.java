package com.projects.payments_app_demo.repository;

import com.projects.payments_app_demo.dtos.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRedisRepository extends CrudRepository<Payment, String> {
}

package com.projects.paymentsapp.repository;

import com.projects.paymentsapp.dtos.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRedisRepository extends CrudRepository<Payment, String> {
}

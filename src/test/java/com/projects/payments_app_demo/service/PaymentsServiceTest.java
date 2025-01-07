package com.projects.payments_app_demo.service;

import com.projects.payments_app_demo.dtos.Payment;
import com.projects.payments_app_demo.repository.PaymentsRedisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceTest {

    @Mock
    PaymentsRedisRepository paymentsRedisRepository;
    @InjectMocks
    PaymentsService paymentsService;

    @Test
    void getPayment() {
        when(paymentsRedisRepository.getPaymentById("id1")).thenReturn(Payment.builder().id("id1").build());
        Assertions.assertEquals("id1", paymentsService.getPayment("id1").getId());
    }
}
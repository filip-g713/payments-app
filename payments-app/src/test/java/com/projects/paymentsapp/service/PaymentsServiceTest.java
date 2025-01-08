package com.projects.paymentsapp.service;


import dtos.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.PaymentsRedisRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceTest {

    @Mock
    PaymentsRedisRepository paymentsRedisRepository;
    @InjectMocks
    PaymentsService paymentsService;

    @Test
    void getPayment() {
        when(paymentsRedisRepository.findById("id1")).thenReturn(Optional.ofNullable(Payment.builder().id("id1").build()));
        Assertions.assertEquals("id1", paymentsService.getPayment("id1").getId());
    }
}
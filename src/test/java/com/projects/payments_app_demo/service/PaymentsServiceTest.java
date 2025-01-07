package com.projects.payments_app_demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentsServiceTest {

    private PaymentsService paymentsService = new PaymentsService();

    @Test
    void getPayment() {
        Assertions.assertEquals("id1", paymentsService.getPayment().getId());
    }
}
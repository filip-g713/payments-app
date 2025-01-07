package com.projects.payments_app_demo.service;

import com.projects.payments_app_demo.dtos.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentsService {

    public Payment getPayment() {
        return Payment.builder()
                .id("id1")
                .amount(new BigDecimal(1234))
                .fromAccount("fromAccount")
                .toAccount("toAccount")
                .instant(true)
                .build();
    }
}

package com.projects.payments_app_demo.controller;

import com.projects.payments_app_demo.dtos.Payment;
import com.projects.payments_app_demo.service.PaymentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;

    @GetMapping
    public ResponseEntity<Payment> getPayments() {
        return ResponseEntity.ok(paymentsService.getPayment());
    }
}

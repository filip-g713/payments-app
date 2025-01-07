package com.projects.payments_app_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @GetMapping
    public ResponseEntity<String> getPayments() {
        return ResponseEntity.ok("Payment sent successfully");
    }
}

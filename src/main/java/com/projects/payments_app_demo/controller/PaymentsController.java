package com.projects.payments_app_demo.controller;

import com.projects.payments_app_demo.dtos.Payment;
import com.projects.payments_app_demo.service.PaymentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayments(@PathVariable String id) {
        return ResponseEntity.ok(paymentsService.getPayment(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createPayment(@RequestBody Payment payment) {
        paymentsService.savePayment(payment);
        return ResponseEntity.noContent().build();
    }

}

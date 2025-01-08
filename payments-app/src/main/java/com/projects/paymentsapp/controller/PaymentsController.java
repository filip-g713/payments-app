package com.projects.paymentsapp.controller;

import com.projects.paymentsapp.service.PaymentsService;
import dtos.Payment;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id) {
        return ResponseEntity.ok(paymentsService.getPayment(id));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.ok(paymentsService.getPayments());
    }

    @PostMapping
    public ResponseEntity<Void> savePayment(@RequestBody Payment payment) {
        paymentsService.savePayment(payment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        paymentsService.deletePaymentById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePayments() {
        paymentsService.deletePayments();
        return ResponseEntity.noContent().build();
    }

}

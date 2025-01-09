package com.projects.paymentsapp.controller;

import com.projects.paymentsapp.validators.PaymentValidatorService;
import com.projects.paymentsapp.service.PaymentsService;
import dtos.Payment;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;
    private final PaymentValidatorService paymentValidatorService;


    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id) {
        return ResponseEntity.ok(paymentsService.getPayment(id));
    }


    @PostMapping("/instant")
    public ResponseEntity<Void> saveInstantPayment(@RequestBody Payment payment) {
        if (!paymentValidatorService.validate(payment)) return ResponseEntity.badRequest().build();
        paymentsService.saveInstantPayment(payment);
        return ResponseEntity.created(URI.create("/payments-app/payments/instant")).build();
    }

    @PostMapping("/scheduled")
    public ResponseEntity<Void> saveScheduledPayment(@RequestBody Payment payment) {
        if (!paymentValidatorService.validate(payment)) return ResponseEntity.badRequest().build();;
        paymentsService.saveScheduledPayment(payment);
        return ResponseEntity.created(URI.create("/payments-app/payments/scheduled")).build();
    }

}

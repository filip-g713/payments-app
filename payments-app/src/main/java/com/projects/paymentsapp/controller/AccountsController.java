package com.projects.paymentsapp.controller;

import com.projects.paymentsapp.service.AccountsService;
import dtos.Account;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Account account) {
        accountsService.save(account);
        return ResponseEntity.ok().build();
    }
}

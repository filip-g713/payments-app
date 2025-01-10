package com.projects.paymentsapp.controller;

import com.projects.paymentsapp.service.AccountsService;
import dtos.Account;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountsService.getAllAccounts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        accountsService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

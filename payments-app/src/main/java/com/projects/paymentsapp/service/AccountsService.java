package com.projects.paymentsapp.service;

import dtos.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.AccountRepository;

@Service
@AllArgsConstructor
public class AccountsService {

    private final AccountRepository accountRepository;

    public void save(Account account) {
        accountRepository.save(account);
    }
}

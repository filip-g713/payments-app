package com.projects.paymentsapp.service;

import dtos.Account;
import dtos.Payment;
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

    public void freezeAccountBalanceForPayment(Payment payment) {
        Account account = accountRepository.findById(payment.getFromAccountId()).get();
        account.setBalance(account.getBalance() - payment.getAmount());
        save(account);
    }
}

package com.projects.paymentsapp.service;

import dtos.Account;
import dtos.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.AccountRepository;

import java.util.List;

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

    public List<Account> getAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }
}

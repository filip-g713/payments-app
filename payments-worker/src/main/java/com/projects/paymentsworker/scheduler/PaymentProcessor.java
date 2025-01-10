package com.projects.paymentsworker.scheduler;

import dtos.Account;
import dtos.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repository.AccountRepository;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentProcessor {

    private final AccountRepository accountRepository;

    public synchronized void processPayment(Payment payment) {
        payment.setCompleted("true");
        Optional<Account> toAccount = accountRepository.findById(payment.getToAccountId());
        toAccount.ifPresentOrElse(
                account -> transferMoney(payment, account),
                () -> handleFailedPayment(payment));
    }

    private void transferMoney(Payment payment, Account account) {
        updateAccountBalance(payment, account);
        payment.setSuccess("true");
        log.info("Payment processsed: {}", payment);
        log.info("Account {} balance after transfer", account);
    }

    private void handleFailedPayment(Payment payment) {
        Optional<Account> fromAccount = accountRepository.findById(payment.getFromAccountId());
        fromAccount.ifPresent(
                account -> updateAccountBalance(payment, account)
        );
        payment.setSuccess("false");
        log.info("Payment not processed, transfer refunded: {}", payment);
    }

    private void updateAccountBalance(Payment payment, Account account) {
        account.setBalance(account.getBalance() + payment.getAmount());
        accountRepository.save(account);
    }
}

package com.projects.paymentsapp.validators;

import dtos.Payment;
import exceptions.DateValidationException;
import exceptions.PaymentValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import repository.AccountRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Log4j2
public class PaymentValidatorService {

    private final AccountRepository accountRepository;

    private final Long EMPTY_SCHEDULED_FOR_VALUE = 0L;

    public boolean validate(Payment payment) {
        try {
            validatePayment(payment);
            validatePaymentAccounts(payment);
            validateBalance(payment);
            validateDateForScheduledPayment(payment);
            return true;
        } catch (PaymentValidationException | DateValidationException e) {
            log.error("Payment validation failed, reason: {}", e.getMessage());
        }
        return false;
    }

    private void validateDateForScheduledPayment(Payment payment) throws DateValidationException {
        if (payment.getScheduledFor() < LocalDate.now().toEpochDay()) {
            throw new DateValidationException("Payment scheduled for cannot be before current date");
        }
    }

    private void validateBalance(Payment payment) throws PaymentValidationException {
        double balance = accountRepository.findById(payment.getFromAccountId()).get().getBalance();
        if (balance < 0) {
            throw new PaymentValidationException("Insufficient balance");
        }
    }

    private void validatePayment(Payment payment) throws PaymentValidationException {
        if (payment.getAmount() <= 0) {
            throw new PaymentValidationException("Amount must be greater than zero");
        }
    }

    private void validatePaymentAccounts(Payment payment) throws PaymentValidationException {
        if (!accountRepository.existsById(payment.getFromAccountId())) {
            throw new PaymentValidationException("From account with id " + payment.getFromAccountId() + " does not exist");
        }
        if (!accountRepository.existsById(payment.getToAccountId())) {
            throw new PaymentValidationException("To account with id " + payment.getToAccountId() + " does not exist");
        }
    }
}

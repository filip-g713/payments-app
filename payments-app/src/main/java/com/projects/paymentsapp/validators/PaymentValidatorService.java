package com.projects.paymentsapp.validators;

import dtos.Payment;
import exceptions.DateValidationException;
import exceptions.PaymentValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import repository.AccountRepository;

import java.math.BigDecimal;
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
            if (EMPTY_SCHEDULED_FOR_VALUE.equals(payment.getScheduledFor())) {
                validateInstantPayment(payment);
            } else {
                validateScheduledPayment(payment);
            }
            return true;
        } catch (PaymentValidationException | DateValidationException e) {
            log.error("Payment validation failed, reson: {}", e.getMessage());
        }
        return false;
    }

    public void validateInstantPayment(Payment payment) throws PaymentValidationException {
        validateBalance(payment);
    }

    public void validateScheduledPayment(Payment payment) throws DateValidationException {
        validateDateForScheduledPayment(payment);
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

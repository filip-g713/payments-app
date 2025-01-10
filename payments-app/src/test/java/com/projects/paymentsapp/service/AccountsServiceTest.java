package com.projects.paymentsapp.service;

import dtos.Account;
import dtos.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.AccountRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountsService accountsService;

    @ParameterizedTest
    @MethodSource("createAccount")
    void save(Account account) {
        accountsService.save(account);
        verify(accountRepository, times(1)).save(account);
    }

    @ParameterizedTest
    @MethodSource("providePayments")
    void freezeAccountBalanceForPayment(Payment payment) {
        when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(Account.builder().id("id1").balance(100).build()));
        accountsService.freezeAccountBalanceForPayment(payment);
        verify(accountRepository, times(1)).findById(any());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void freezeAccountBalanceForPaymentShouldThrowNoSuchElementException() {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> accountsService.freezeAccountBalanceForPayment(new Payment()));
    }

    @ParameterizedTest
    @MethodSource("createAccount")
    void getAllAccounts(Account account) {
        when(accountRepository.findAll()).thenReturn(Collections.singletonList(account));
        Assertions.assertNotNull(accountsService.getAllAccounts());
        Assertions.assertEquals(account, accountsService.getAllAccounts().get(0));
    }

    @Test
    void deleteById() {
        doNothing().when(accountRepository).deleteById(any());
        accountsService.deleteById("id");
        verify(accountRepository, times(1)).deleteById(any());
    }

    private static Stream<Arguments> createAccount() {
        return Stream.of(Arguments.of(Account.builder().id("id1").balance(100).build()));
    }

    private static Stream<Arguments> providePayments() {
        return Stream.of(
                Arguments.of(Payment.builder().toAccountId("id1").fromAccountId("id2").amount(1234).scheduledFor(LocalDate.now().toEpochDay()).build())
        );
    }
}
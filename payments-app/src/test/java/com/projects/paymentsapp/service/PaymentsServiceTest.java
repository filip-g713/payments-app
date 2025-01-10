package com.projects.paymentsapp.service;


import dtos.Payment;
import dtos.PaymentJobUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.PaymentsRedisRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceTest {

    @Mock
    PaymentsRedisRepository paymentsRedisRepository;
    @Mock
    PaymentsJobServiceFactory paymentsJobServiceFactory;
    @Mock
    AccountsService accountsService;
    @Mock
    InstantPaymentsJobService instantPaymentsJobService;
    @Mock
    ScheduledPaymentsJobService scheduledPaymentsJobService;
    @InjectMocks
    PaymentsService paymentsService;

    @Test
    void getPayment() {
        when(paymentsRedisRepository.findById("id1")).thenReturn(Optional.ofNullable(Payment.builder()
                .id("id1")
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(1234)
                .build())
        );
        Assertions.assertEquals("id1", paymentsService.getPayment("id1").getId());
    }

    @ParameterizedTest
    @MethodSource("providePayments")
    public void saveScheduledPayment(Payment payment) {
        when(paymentsJobServiceFactory.getPaymentsJobService(ScheduledPaymentsJobService.BEAN_ID)).thenReturn(scheduledPaymentsJobService);
        when(paymentsRedisRepository.save(payment)).thenReturn(payment);
        doNothing().when(accountsService).freezeAccountBalanceForPayment(payment);
        paymentsService.saveScheduledPayment(payment);
        verify(paymentsRedisRepository, times(1)).save(payment);
        verify(accountsService, times(1)).freezeAccountBalanceForPayment(payment);
        verify(scheduledPaymentsJobService, times(1)).createPaymentJob(payment);
    }

    @ParameterizedTest
    @MethodSource("providePayments")
    public void saveInstantPayment(Payment payment) {
        when(paymentsJobServiceFactory.getPaymentsJobService(InstantPaymentsJobService.BEAN_ID)).thenReturn(instantPaymentsJobService);
        when(paymentsRedisRepository.save(payment)).thenReturn(payment);
        doNothing().when(accountsService).freezeAccountBalanceForPayment(payment);
        paymentsService.saveInstantPayment(payment);
        verify(paymentsRedisRepository, times(1)).save(payment);
        verify(accountsService, times(1)).freezeAccountBalanceForPayment(payment);
        verify(instantPaymentsJobService, times(1)).createPaymentJob(payment);
    }

    @Test
    public void cancelPayment() {
        when(paymentsJobServiceFactory.getPaymentsJobService(ScheduledPaymentsJobService.BEAN_ID)).thenReturn(scheduledPaymentsJobService);
        when(scheduledPaymentsJobService.getPaymentJob("id")).thenReturn(PaymentJobUnit.builder().id("id").paymentId("paymentId").build());
        paymentsService.cancelPayment("id");
        verify(scheduledPaymentsJobService, times(1)).cancelPaymentJob("id");
        verify(paymentsRedisRepository, times(1)).deleteById("paymentId");
    }

    private static Stream<Arguments> providePayments() {
        return Stream.of(
                Arguments.of(Payment.builder().id("id").toAccountId("id1").fromAccountId("id2").amount(1234).scheduledFor(LocalDate.now().toEpochDay()).build())
        );
    }
}
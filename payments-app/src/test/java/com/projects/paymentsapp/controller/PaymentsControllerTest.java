package com.projects.paymentsapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.paymentsapp.service.PaymentsService;
import com.projects.paymentsapp.validators.PaymentValidatorService;
import dtos.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PaymentsControllerTest {

    @Mock
    PaymentsService paymentsService;
    @Mock
    PaymentValidatorService paymentValidatorService;


    @InjectMocks
    PaymentsController paymentsController;

    MockMvc mockMvc;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentsController).build();
        mapper = new ObjectMapper();
    }

    @Test
    void getPaymentByIdShouldBe200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/id1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("providePayments")
    void scheduleInstantPaymentShouldBe400(Payment payment) throws Exception {
        when(paymentValidatorService.validate(any())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/payments/instant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payment)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("providePayments")
    void scheduleInstantPaymentShouldBe200(Payment payment) throws Exception {
        when(paymentValidatorService.validate(payment)).thenReturn(true);
        doNothing().when(paymentsService).saveInstantPayment(payment);
        mockMvc.perform(MockMvcRequestBuilders.post("/payments/instant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payment)))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("providePayments")
    void scheduleScheduledPaymentShouldBe400(Payment payment) throws Exception {
        when(paymentValidatorService.validate(any())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/payments/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payment)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("providePayments")
    void scheduleScheduledPaymentShouldBe200(Payment payment) throws Exception {
        when(paymentValidatorService.validate(payment)).thenReturn(true);
        doNothing().when(paymentsService).saveScheduledPayment(payment);
        mockMvc.perform(MockMvcRequestBuilders.post("/payments/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payment)))
                .andExpect(status().isCreated());
    }

    @Test
    void cancelPaymentByIdShouldBe200() throws Exception {
        doNothing().when(paymentsService).cancelPayment(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/payments/cancel/id1"))
                .andExpect(status().isOk());
    }

    private static Stream<Arguments> providePayments() {
        return Stream.of(
                Arguments.of(Payment.builder().toAccountId("id1").fromAccountId("id2").amount(1234).scheduledFor(LocalDate.now().toEpochDay()).build())
        );
    }
}
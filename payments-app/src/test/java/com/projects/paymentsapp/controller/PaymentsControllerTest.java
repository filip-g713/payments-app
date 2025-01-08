package com.projects.paymentsapp.controller;

import com.projects.payments_app_demo.service.PaymentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentsControllerTest {

    @Mock
    PaymentsService paymentsService;

    @InjectMocks
    PaymentsController paymentsController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentsController).build();
    }

    @Test
    void getPaymentByIdShouldBe200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/id1"))
                .andExpect(status().isOk());
    }

}
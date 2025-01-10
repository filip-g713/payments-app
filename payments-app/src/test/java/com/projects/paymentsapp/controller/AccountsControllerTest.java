package com.projects.paymentsapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.paymentsapp.service.AccountsService;
import dtos.Account;
import jodd.introspector.Mapper;
import org.junit.jupiter.api.Assertions;
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

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountsControllerTest {

    @Mock
    AccountsService accountsService;

    @InjectMocks
    AccountsController accountsController;

    MockMvc mockMvc;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountsController).build();
        mapper = new ObjectMapper();
    }

    @Test
    void saveShouldReturn200() throws Exception {
        doNothing().when(accountsService).save(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("createAccount")
    void getAllShouldReturn200AndSingleAccount(Account account) throws Exception {
        when(accountsService.getAllAccounts()).thenReturn(Collections.singletonList(account));
        String stringResponse = mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(stringResponse, mapper.writeValueAsString(Collections.singletonList(account)));
    }

    @Test
    void deleteShouldReturn200() throws Exception {
        doNothing().when(accountsService).deleteById(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/id1"))
                .andExpect(status().isOk());
    }


    private static Stream<Arguments> createAccount() {
        return Stream.of(Arguments.of(Account.builder().id("id1").balance(100).build()));
    }

}
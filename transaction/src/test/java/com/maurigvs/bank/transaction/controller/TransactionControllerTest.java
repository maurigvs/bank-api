package com.maurigvs.bank.transaction.controller;

import com.maurigvs.bank.transaction.JsonMapper;
import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.service.TransactionService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService service;

    @Test
    void should_return_Created_when_post_TransactionRequest() throws Exception {
        var request = new TransactionRequest(1L, 1L, "Initial deposit", 150.00);
        var json = new JsonMapper().apply(request);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        then(service).should(times(1)).create(any(Transaction.class));
        then(service).shouldHaveNoMoreInteractions();
    }
}
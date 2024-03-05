package com.maurigvs.bank.transaction.controller;

import com.maurigvs.bank.transaction.JsonMapper;
import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.dto.TransactionResponse;
import com.maurigvs.bank.transaction.grpc.AccountApiService;
import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.model.Customer;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @MockBean
    AccountApiService accountApiService;

    @Test
    void should_return_Created_when_post_TransactionRequest() throws Exception {
        var request = new TransactionRequest(1L, 1L, "Initial deposit", 150.00);
        var json = new JsonMapper().apply(request);
        var account = new Account(1L, new Customer(1L));
        given(accountApiService.findById(anyLong())).willReturn(account);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        then(accountApiService).should(times(1)).findById(1L);
        then(accountApiService).shouldHaveNoMoreInteractions();
        then(transactionService).should(times(1)).create(any(Transaction.class));
        then(transactionService).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_OK_when_get_transactions_by_account_id() throws Exception {
        var response = new TransactionResponse(
                "27/02/2024 15:12",
                "Initial deposit",
                1L ,
                150.00);
        var json = new JsonMapper().apply(List.of(response));

        var transaction = new Transaction(1L,
                LocalDateTime.of(2024,2,27,15,12),
                "Initial deposit",
                150.00,
                new Account(1L, new Customer(1L)));
        given(transactionService.findByAccountId(anyLong())).willReturn(List.of(transaction));

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));

        then(transactionService).should(times(1)).findByAccountId(1L);
        then(transactionService).shouldHaveNoMoreInteractions();
    }
}
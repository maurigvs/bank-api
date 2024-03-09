package com.maurigvs.bank.transaction.controller;

import com.maurigvs.bank.transaction.JsonMapper;
import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.dto.TransactionResponse;
import com.maurigvs.bank.transaction.model.AccountHolder;
import com.maurigvs.bank.transaction.model.CheckingAccount;
import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.service.CheckingAccountService;
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
    CheckingAccountService accountService;

    @Test
    void should_return_Created_when_post_TransactionRequest() throws Exception {
        var request = new TransactionRequest(1L, "Initial deposit", 150.00);
        var json = new JsonMapper().apply(request);
        var checkingAccount = new CheckingAccount(1L, new AccountHolder(1L));
        given(accountService.findById(anyLong())).willReturn(checkingAccount);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        then(accountService).should(times(1)).findById(1L);
        then(accountService).shouldHaveNoMoreInteractions();
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

        var checkingAccount = new CheckingAccount(1L, new AccountHolder(1L));
        var transaction = new Transaction(1L,
                LocalDateTime.of(2024,2,27,15,12),
                "Initial deposit", 150.00, checkingAccount);
        checkingAccount.getTransactionList().add(transaction);
        given(accountService.findById(anyLong())).willReturn(checkingAccount);

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));

        then(accountService).should(times(1)).findById(1L);
        then(accountService).shouldHaveNoMoreInteractions();
        then(transactionService).shouldHaveNoInteractions();
    }
}
package com.maurigvs.bank.checkingaccount.controller;

import com.maurigvs.bank.checkingaccount.JsonMapper;
import com.maurigvs.bank.checkingaccount.dto.AccountRequest;
import com.maurigvs.bank.checkingaccount.dto.AccountResponse;
import com.maurigvs.bank.checkingaccount.grpc.client.AccountHolderGrpcClient;
import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.checkingaccount.service.CheckingAccountService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckingAccountController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CheckingAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CheckingAccountService service;

    @MockBean
    AccountHolderGrpcClient grpcClient;

    private static final String URL_PATH = "/checking-account";

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    @Test
    void should_return_Created_when_post_CheckingAccount() throws Exception {
        var request = new AccountRequest("12345", 12345);
        var json = JSON_MAPPER.apply(request);
        var accountHolder = new AccountHolder(1L, "12345");
        given(grpcClient.findByTaxId(anyString())).willReturn(accountHolder);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        then(grpcClient).should().findByTaxId(request.taxId());
        then(grpcClient).shouldHaveNoMoreInteractions();
        then(service).should().openAccount(any(CheckingAccount.class));
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_Ok_when_get_CheckingAccount_list() throws Exception {
        var request = new AccountResponse(1L, "123456", "25/02/2024", 0.0);
        var json = JSON_MAPPER.apply(List.of(request));

        var accountHolder = new AccountHolder(1L, "123456");
        var checkingAccount = new CheckingAccount(1L, LocalDate.of(2024,2,25), 123456, accountHolder);
        given(service.findAllAccounts()).willReturn(List.of(checkingAccount));

        mockMvc.perform(get(URL_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));

        then(service).should().findAllAccounts();
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_Ok_when_delete_CheckingAccount_by_Id() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(URL_PATH + "/" + id))
                .andExpect(status().isOk());

        then(service).should().closeAccount(id);
        then(service).shouldHaveNoMoreInteractions();
    }

}

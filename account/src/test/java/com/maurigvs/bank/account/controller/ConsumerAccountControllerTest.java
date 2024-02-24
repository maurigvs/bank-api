package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.JsonMapper;
import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.service.ConsumerAccountService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsumerAccountController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConsumerAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConsumerAccountService service;

    private static final String URL_PATH = "/consumer";

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    @Test
    void should_return_Created_when_post_ConsumerAccount() throws Exception {
        var request = new AccountRequest("12345", 12345);
        var json = JSON_MAPPER.apply(request);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service).openAccount(any(ConsumerAccount.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_put_ConsumerAccount_by_Id() throws Exception {
        var request = new AccountResponse(1L, "12345", LocalDate.now().toString());
        var json = JSON_MAPPER.apply(List.of(request));

        var account = new ConsumerAccount(1L, "12345", LocalDate.now());
        given(service.findAllAccounts()).willReturn(List.of(account));

        mockMvc.perform(get(URL_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(service).findAllAccounts();
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_get_ConsumerAccount_list() throws Exception {
        var id = 1L;
        var request = new AccountRequest("12345", 12345);
        var json = JSON_MAPPER.apply(request);

        mockMvc.perform(put(URL_PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(service).updateAccount(anyLong(), any(ConsumerAccount.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_delete_ConsumerAccount_by_Id() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(URL_PATH + "/" + id))
                .andExpect(status().isOk());

        verify(service).closeAccount(id);
        verifyNoMoreInteractions(service);
    }

}

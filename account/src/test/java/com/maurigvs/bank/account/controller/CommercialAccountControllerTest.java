package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.Utils;
import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.model.CommercialAccount;
import com.maurigvs.bank.account.service.CommercialAccountService;
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

@WebMvcTest(CommercialAccountController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommercialAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommercialAccountService service;

    private static final String URL_PATH = "/commercial";

    @Test
    void should_return_Created_when_post_CommercialAccount() throws Exception {
        var request = new AccountRequest("12345", 12345);
        var json = Utils.ofJson(request);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service).openAccount(any(CommercialAccount.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_put_CommercialAccount_by_Id() throws Exception {
        var account = new CommercialAccount(1L, "12345", LocalDate.now());
        var response = new AccountResponse(1L, "12345", LocalDate.now().toString());
        var json = Utils.ofJson(List.of(response));
        given(service.findAllAccounts()).willReturn(List.of(account));

        mockMvc.perform(get(URL_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(service).findAllAccounts();
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_get_CommercialAccount_list() throws Exception {
        var id = 1L;
        var request = new AccountRequest("12345", 12345);
        var json = Utils.ofJson(request);

        mockMvc.perform(put(URL_PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(service).updateAccount(anyLong(), any(CommercialAccount.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_delete_CommercialAccount_by_Id() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(URL_PATH + "/" + id))
                .andExpect(status().isOk());

        verify(service).closeAccount(id);
        verifyNoMoreInteractions(service);
    }

}
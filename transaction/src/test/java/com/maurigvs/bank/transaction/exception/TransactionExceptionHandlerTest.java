package com.maurigvs.bank.transaction.exception;

import com.maurigvs.bank.transaction.JsonMapper;
import com.maurigvs.bank.transaction.controller.TransactionController;
import com.maurigvs.bank.transaction.dto.ErrorResponse;
import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.grpc.AccountApiService;
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

import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @MockBean
    AccountApiService accountApiService;

    private static final String URL_PATH = "/";

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    @Test
    void should_return_NotFound_when_EntityNotFoundException_is_thrown() throws Exception {
        var request = new TransactionRequest(1L, 1L, "Initial deposit", 100.00);
        var response = new ErrorResponse("Not Found", "Account not found by Id 1");
        willThrow(new EntityNotFoundException("Account", "Id", "1")).given(accountApiService).findById(1L);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_MAPPER.apply(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON_MAPPER.apply(response)));
    }

    @Test
    void should_return_InternalServerError_when_RuntimeException_is_thrown() throws Exception {
        var request = new TransactionRequest(1L, 1L, "Initial deposit", 100.00);
        var response = new ErrorResponse("Internal Server Error", "RuntimeException: Internal error");
        willThrow(new RuntimeException("Internal error")).given(accountApiService).findById(1L);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_MAPPER.apply(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON_MAPPER.apply(response)));
    }
}
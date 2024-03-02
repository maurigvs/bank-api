package com.maurigvs.bank.account.exception;

import com.maurigvs.bank.account.JsonMapper;
import com.maurigvs.bank.account.controller.ConsumerAccountController;
import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.ErrorResponse;
import com.maurigvs.bank.account.service.ConsumerAccountService;
import com.maurigvs.bank.account.service.CustomerService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsumerAccountController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConsumerAccountService accountService;

    @MockBean
    CustomerService customerService;

    private static final String URL_PATH = "/consumer";

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    @Test
    void should_return_Not_Found_when_EntityNotFoundException_is_thrown() throws Exception {
        var response = new ErrorResponse("Not Found", "Account not found by Id 1");
        var json = JSON_MAPPER.apply(response);

        willThrow(new EntityNotFoundException("Account", "Id", "1")).given(accountService).closeAccount(any());

        mockMvc.perform(delete(URL_PATH + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @Test
    void should_return_Bad_Request_when_MethodArgumentNotValidException_is_thrown() throws Exception {
        var request = new AccountRequest("", 12345);
        var jsonRequest = JSON_MAPPER.apply(request);

        var response = new ErrorResponse("Bad Request", "[taxId] must not be blank");
        var jsonResponse = JSON_MAPPER.apply(response);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void should_return_Internal_Server_Error_when_RuntimeException_is_thrown() throws Exception {
        var response = new ErrorResponse("Internal Server Error", "RuntimeException: Some Runtime Exception");
        var json = JSON_MAPPER.apply(response);

        given(accountService.findAllAccounts()).willThrow(new RuntimeException("Some Runtime Exception"));

        mockMvc.perform(get(URL_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

}

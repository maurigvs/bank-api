package com.maurigvs.bank.customer.controller;

import com.maurigvs.bank.customer.JsonMapper;
import com.maurigvs.bank.customer.dto.CompanyRequest;
import com.maurigvs.bank.customer.dto.CompanyResponse;
import com.maurigvs.bank.customer.model.Company;
import com.maurigvs.bank.customer.service.CompanyService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CompanyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CompanyService service;

    private static final String URL_PATH = "/company";

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    @Test
    void should_return_Created_when_post_Company() throws Exception {
        var request = new CompanyRequest("12345", "Company", "Company Inc.", "01/01/2014");
        var json = JSON_MAPPER.apply(request);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service, times(1)).create(any(Company.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_get_all_Companies() throws Exception {
        var response = new CompanyResponse(1L,
                "12345",
                "Company",
                "Company Inc.",
                "01/01/2014",
                "25/02/2024");
        var json = JSON_MAPPER.apply(List.of(response));

        var company = new Company(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "Company",
                "Company Inc.",
                LocalDate.of(2014,1,1));
        given(service.findAll()).willReturn(List.of(company));

        mockMvc.perform(get(URL_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(service, times(1)).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void should_return_Ok_when_delete_Company_by_Id() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(URL_PATH + "/" + id))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(id);
        verifyNoMoreInteractions(service);
    }
}
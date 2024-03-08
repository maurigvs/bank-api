package com.maurigvs.bank.accountholder.controller;

import com.maurigvs.bank.accountholder.JsonMapper;
import com.maurigvs.bank.accountholder.dto.PersonRequest;
import com.maurigvs.bank.accountholder.dto.PersonResponse;
import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.service.PersonService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    private static final String URL_PATH = "/person";

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    @Test
    void should_return_Created_when_post_Person() throws Exception {
        var request = new PersonRequest("12345", "John", "Snow", "28/07/1987");
        var json = JSON_MAPPER.apply(request);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        then(personService).should().create(any(Person.class));
        then(personService).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_Ok_when_get_all_Persons() throws Exception {
        var response = new PersonResponse(1L,
                "12345",
                "John",
                "Snow",
                "28/07/1987",
                "25/02/2024");
        var json = JSON_MAPPER.apply(List.of(response));

        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));
        given(personService.findAll()).willReturn(List.of(person));

        mockMvc.perform(get(URL_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));

        then(personService).should().findAll();
        then(personService).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_Ok_when_delete_Person_by_Id() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(URL_PATH + "/" + id))
                .andExpect(status().isOk());

        then(personService).should().deleteById(id);
        then(personService).shouldHaveNoMoreInteractions();
    }
}

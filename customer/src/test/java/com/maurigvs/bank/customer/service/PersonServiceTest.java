package com.maurigvs.bank.customer.service;

import com.maurigvs.bank.customer.exception.EntityNotFoundException;
import com.maurigvs.bank.customer.model.Person;
import com.maurigvs.bank.customer.repository.PersonRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {PersonService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonServiceTest {

    @Autowired
    PersonService service;

    @MockBean
    PersonRepository repository;

    @Test
    void should_create_Person() {
        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));

        service.create(person);

        then(repository).should().save(person);
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_Person_by_Id() {
        var taxId = "12345";
        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));
        given(repository.findByTaxId(anyString())).willReturn(Optional.of(person));

        var result = service.findByTaxId(taxId);

        then(repository).should().findByTaxId(taxId);
        then(repository).shouldHaveNoMoreInteractions();
        assertSame(person, result);
    }

    @Test
    void should_throw_EntityNotFoundException_when_Person_not_found_by_Id() {
        var taxId = "12345";
        given(repository.findByTaxId(anyString())).willReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> service.findByTaxId(taxId));

        assertEquals("Person not found by taxId 12345", exception.getMessage());
    }

    @Test
    void should_return_Person_list() {
        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));
        var personList = List.of(person);
        given(repository.findAll()).willReturn(personList);

        var result = service.findAll();

        then(repository).should().findAll();
        then(repository).shouldHaveNoMoreInteractions();
        assertSame(personList, result);
    }

    @Test
    void should_update_Person_by_Id() {
        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));

        service.updateById(1L, person);

        then(repository).should().save(person);
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_delete_Person_by_Id() {
        var id = 1L;
        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));
        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        service.deleteById(id);

        then(repository).should().findById(id);
        then(repository).should().delete(person);
        then(repository).shouldHaveNoMoreInteractions();
    }
}
package com.maurigvs.bank.accountholder.service;

import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.repository.PersonRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {PersonServiceImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonServiceTest {

    @Autowired
    PersonService personService;

    @MockBean
    PersonRepository personRepository;

    @Test
    void should_create_Person() {
        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));

        personService.create(person);

        then(personRepository).should().save(person);
        then(personRepository).shouldHaveNoMoreInteractions();
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
        given(personRepository.findByTaxId(anyString())).willReturn(Optional.of(person));

        var result = personService.findByTaxId(taxId);

        then(personRepository).should().findByTaxId(taxId);
        then(personRepository).shouldHaveNoMoreInteractions();
        assertSame(person, result);
    }

    @Test
    void should_throw_EntityNotFoundException_when_Person_not_found_by_Id() {
        var taxId = "12345";
        given(personRepository.findByTaxId(anyString())).willReturn(Optional.empty());

        var exception = assertThrows(NoSuchElementException.class, () -> personService.findByTaxId(taxId));

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
        given(personRepository.findAll()).willReturn(personList);

        var result = personService.findAll();

        then(personRepository).should().findAll();
        then(personRepository).shouldHaveNoMoreInteractions();
        assertSame(personList, result);
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
        given(personRepository.findById(anyLong())).willReturn(Optional.of(person));

        personService.deleteById(id);

        then(personRepository).should().findById(id);
        then(personRepository).should().delete(person);
        then(personRepository).shouldHaveNoMoreInteractions();
    }
}
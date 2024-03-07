package com.maurigvs.bank.customer.mapper;

import com.maurigvs.bank.customer.model.Person;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonResponseMapperTest {

    private static final LocalDateMapper LOCAL_DATE_MAPPER = new LocalDateMapper();

    @Test
    void should_return_PersonResponse_given_an_Person() {
        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));

        var joinedAt = LOCAL_DATE_MAPPER.reverse(person.getJoinedAt());
        var birthDate = LOCAL_DATE_MAPPER.reverse(person.getBirthDate());

        var result = new PersonResponseMapper().apply(person);

        assertEquals(person.getId(), result.id());
        assertEquals(person.getTaxId(), result.taxId());
        assertEquals(person.getName(), result.name());
        assertEquals(person.getSurname(), result.surname());
        assertEquals(birthDate, result.birthDate());
        assertEquals(joinedAt, result.joinedAt());
    }
}
package com.maurigvs.bank.customer.mapper;

import com.maurigvs.bank.customer.dto.PersonRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PersonMapperTest {

    @Test
    void should_return_Person_given_an_PersonRequest() {
        var request = new PersonRequest("12345", "John", "Snow", "28/07/1987");
        var birthDate = LocalDate.of(1987,7,28);
        var joinedAt = LocalDate.now();
        
        var result = new PersonMapper().apply(request);

        assertNull(result.getId());
        assertEquals(request.taxId(), result.getTaxId());
        assertEquals(request.name(), result.getName());
        assertEquals(request.surname(), result.getSurname());
        assertEquals(birthDate, result.getBirthDate());
        assertEquals(joinedAt, result.getJoinedAt());
    }
}
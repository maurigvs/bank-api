package com.maurigvs.bank.customer.mapper;

import com.maurigvs.bank.customer.dto.PersonResponse;
import com.maurigvs.bank.customer.model.Person;

import java.util.function.Function;

public class PersonResponseMapper implements Function<Person, PersonResponse> {

    private static final LocalDateMapper DATE_MAPPER = new LocalDateMapper();

    @Override
    public PersonResponse apply(Person person) {
        var birthDate = DATE_MAPPER.reverse(person.getBirthDate());
        var joinedAt = DATE_MAPPER.reverse(person.getJoinedAt());

        return new PersonResponse(
                person.getId(),
                person.getTaxId(),
                person.getName(),
                person.getSurname(),
                birthDate,
                joinedAt);
    }
}

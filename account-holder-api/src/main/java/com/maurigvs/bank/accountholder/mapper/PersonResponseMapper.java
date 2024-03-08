package com.maurigvs.bank.accountholder.mapper;

import com.maurigvs.bank.accountholder.dto.PersonResponse;
import com.maurigvs.bank.accountholder.model.Person;

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

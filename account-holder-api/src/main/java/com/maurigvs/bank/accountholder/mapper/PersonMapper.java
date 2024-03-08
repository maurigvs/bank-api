package com.maurigvs.bank.accountholder.mapper;

import com.maurigvs.bank.accountholder.dto.PersonRequest;
import com.maurigvs.bank.accountholder.model.Person;

import java.time.LocalDate;
import java.util.function.Function;

public class PersonMapper implements Function<PersonRequest, Person> {

    private static final LocalDateMapper DATE_MAPPER = new LocalDateMapper();

    @Override
    public Person apply(PersonRequest request) {
        var joinedAt = LocalDate.now();
        var birthDate = DATE_MAPPER.apply(request.birthDate());

        return new Person(null,
                request.taxId(),
                joinedAt,
                request.name(),
                request.surname(),
                birthDate);
    }
}

package com.maurigvs.bank.customer.mapper;

import com.maurigvs.bank.customer.model.Company;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CompanyResponseMapperTest {

    private static final LocalDateMapper LOCAL_DATE_MAPPER = new LocalDateMapper();

    @Test
    void should_return_CompanyResponse_given_an_Company() {
        var company = new Company(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "Company",
                "Company Inc.",
                LocalDate.of(2014,1,1));

        var joinedAt = LOCAL_DATE_MAPPER.reverse(company.getJoinedAt());
        var openingDate = LOCAL_DATE_MAPPER.reverse(company.getOpeningDate());

        var result = new CompanyResponseMapper().apply(company);

        assertEquals(company.getId(), result.id());
        assertEquals(company.getTaxId(), result.taxId());
        assertEquals(company.getBusinessName(), result.businessName());
        assertEquals(company.getLegalName(), result.legalName());
        assertEquals(openingDate, result.openingDate());
        assertEquals(joinedAt, result.joinedAt());
    }
}
package com.maurigvs.bank.customer.mapper;

import com.maurigvs.bank.customer.dto.CompanyRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CompanyMapperTest {

    @Test
    void should_return_Company_given_an_CompanyRequest() {
        var request = new CompanyRequest("12345", "Company", "Company Inc.", "01/01/2014");
        var openingDate = LocalDate.of(2014,1,1);
        var joinedAt = LocalDate.now();

        var result = new CompanyMapper().apply(request);

        assertNull(result.getId());
        assertEquals(request.taxId(), result.getTaxId());
        assertEquals(request.businessName(), result.getBusinessName());
        assertEquals(request.legalName(), result.getLegalName());
        assertEquals(openingDate, result.getOpeningDate());
        assertEquals(joinedAt, result.getJoinedAt());
    }
}
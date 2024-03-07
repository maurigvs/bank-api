package com.maurigvs.bank.customer.mapper;

import com.maurigvs.bank.customer.dto.CompanyResponse;
import com.maurigvs.bank.customer.model.Company;

import java.util.function.Function;

public class CompanyResponseMapper implements Function<Company, CompanyResponse> {

    private static final LocalDateMapper DATE_MAPPER = new LocalDateMapper();

    @Override
    public CompanyResponse apply(Company company) {
        var openingDate = DATE_MAPPER.reverse(company.getOpeningDate());
        var joinedAt = DATE_MAPPER.reverse(company.getJoinedAt());

        return new CompanyResponse(
                company.getId(),
                company.getTaxId(),
                company.getBusinessName(),
                company.getLegalName(),
                openingDate,
                joinedAt);
    }
}

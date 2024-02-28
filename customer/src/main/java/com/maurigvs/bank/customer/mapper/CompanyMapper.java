package com.maurigvs.bank.customer.mapper;

import com.maurigvs.bank.customer.dto.CompanyRequest;
import com.maurigvs.bank.customer.model.Company;

import java.time.LocalDate;
import java.util.function.Function;

public class CompanyMapper implements Function<CompanyRequest, Company> {

    private static final LocalDateMapper DATE_MAPPER = new LocalDateMapper();

    @Override
    public Company apply(CompanyRequest request) {
        var joinedAt = LocalDate.now();
        var openingDate = DATE_MAPPER.apply(request.openingDate());

        return new Company(null,
                request.taxId(),
                joinedAt,
                request.businessName(),
                request.legalName(),
                openingDate);
    }
}

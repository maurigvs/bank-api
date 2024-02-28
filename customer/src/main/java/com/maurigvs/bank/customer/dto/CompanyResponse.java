package com.maurigvs.bank.customer.dto;

public record CompanyResponse(
        Long id,
        String taxId,
        String businessName,
        String legalName,
        String openingDate,
        String joinedAt
) {
}

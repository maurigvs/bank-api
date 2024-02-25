package com.maurigvs.bank.customer.dto;

public record CompanyRequest(
        String taxId,
        String businessName,
        String legalName,
        String openingDate
) {
}

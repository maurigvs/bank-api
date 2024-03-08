package com.maurigvs.bank.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(

        @NotBlank
        String taxId,

        @NotBlank
        String businessName,

        @NotBlank
        String legalName,

        @NotBlank
        String openingDate
) {
}

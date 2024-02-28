package com.maurigvs.bank.customer.dto;

public record PersonResponse(
        Long id,
        String taxId,
        String name,
        String surname,
        String birthDate,
        String joinedAt
) {
}

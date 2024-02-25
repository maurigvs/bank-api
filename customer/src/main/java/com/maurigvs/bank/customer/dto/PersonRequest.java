package com.maurigvs.bank.customer.dto;

public record PersonRequest(
        String taxId,
        String name,
        String surname,
        String birthDate
) {
}

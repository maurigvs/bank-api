package com.maurigvs.bank.accountholder.dto;

public record PersonResponse(
        Long id,
        String taxId,
        String name,
        String surname,
        String birthDate,
        String joinedAt
) {
}

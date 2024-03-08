package com.maurigvs.bank.account.dto;

public record AccountResponse(
        Long accountId,
        String taxId,
        String joinedAt,
        Double balance
) {
}

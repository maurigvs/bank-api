package com.maurigvs.bank.checkingaccount.dto;

public record AccountResponse(
        Long accountId,
        String taxId,
        String joinedAt,
        Double balance
) {
}

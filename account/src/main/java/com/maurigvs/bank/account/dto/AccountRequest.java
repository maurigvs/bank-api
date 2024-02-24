package com.maurigvs.bank.account.dto;

public record AccountRequest(
        String taxId,
        Integer pinCode
) {
}

package com.maurigvs.bank.transaction.dto;

public record TransactionResponse(
        String dateTime,
        String description,
        Long refId,
        Double amount
) {
}

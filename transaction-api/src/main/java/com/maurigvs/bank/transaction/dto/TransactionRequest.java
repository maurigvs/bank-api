package com.maurigvs.bank.transaction.dto;

public record TransactionRequest(
        Long accountHolderId,
        Long checkingAccountId,
        String description,
        Double amount
){
}

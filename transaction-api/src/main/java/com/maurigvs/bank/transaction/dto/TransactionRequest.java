package com.maurigvs.bank.transaction.dto;

public record TransactionRequest(
        Long customerId,
        Long accountId,
        String description,
        Double amount
){
}

package com.maurigvs.bank.transaction.dto;

public record ErrorResponse(
        String error,
        String message
){
}

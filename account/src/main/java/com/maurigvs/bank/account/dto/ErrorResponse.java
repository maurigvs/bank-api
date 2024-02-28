package com.maurigvs.bank.account.dto;

public record ErrorResponse(
        String error,
        String message
){
}

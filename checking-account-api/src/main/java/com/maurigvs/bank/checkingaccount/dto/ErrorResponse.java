package com.maurigvs.bank.checkingaccount.dto;

public record ErrorResponse(
        String error,
        String message
){
}

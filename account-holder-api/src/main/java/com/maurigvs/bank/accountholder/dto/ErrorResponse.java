package com.maurigvs.bank.accountholder.dto;

public record ErrorResponse(
        String error,
        String message
) {
}

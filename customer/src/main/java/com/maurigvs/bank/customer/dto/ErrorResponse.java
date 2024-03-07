package com.maurigvs.bank.customer.dto;

public record ErrorResponse(
        String error,
        String message
) {
}

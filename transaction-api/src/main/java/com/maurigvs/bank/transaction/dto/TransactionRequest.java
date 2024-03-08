package com.maurigvs.bank.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(

        @NotNull
        Long checkingAccountId,

        @NotBlank
        String description,

        @NotNull
        Double amount
){
}

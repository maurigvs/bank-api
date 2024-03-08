package com.maurigvs.bank.checkingaccount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(

        @NotBlank
        String taxId,

        @NotNull
        Integer pinCode
) {
}

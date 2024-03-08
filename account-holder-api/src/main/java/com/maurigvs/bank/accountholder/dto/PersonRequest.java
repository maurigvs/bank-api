package com.maurigvs.bank.accountholder.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonRequest(

        @NotBlank
        String taxId,

        @NotBlank
        String name,

        @NotBlank
        String surname,

        @NotBlank
        String birthDate
) {
}

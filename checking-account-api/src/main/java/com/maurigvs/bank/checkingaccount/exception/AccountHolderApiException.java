package com.maurigvs.bank.checkingaccount.exception;

import io.grpc.StatusRuntimeException;

public class AccountHolderApiException extends RuntimeException {

    public AccountHolderApiException(StatusRuntimeException exception) {
        super(exception.getMessage() + " > " + exception.getCause().getLocalizedMessage());
    }
}

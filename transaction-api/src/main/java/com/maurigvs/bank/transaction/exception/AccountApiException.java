package com.maurigvs.bank.transaction.exception;

import io.grpc.StatusRuntimeException;

public class AccountApiException extends RuntimeException {

    public AccountApiException(StatusRuntimeException exception) {
        super(exception.getMessage() + " > " + exception.getCause().getLocalizedMessage());
    }
}

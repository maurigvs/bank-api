package com.maurigvs.bank.transaction.exception;

import io.grpc.StatusRuntimeException;

public class CheckingAccountApiException extends RuntimeException {

    public CheckingAccountApiException(StatusRuntimeException exception) {
        super(exception.getMessage() + " > " + exception.getCause().getLocalizedMessage());
    }
}

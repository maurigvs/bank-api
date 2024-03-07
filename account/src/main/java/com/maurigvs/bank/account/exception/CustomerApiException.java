package com.maurigvs.bank.account.exception;

import io.grpc.StatusRuntimeException;

public class CustomerApiException extends RuntimeException {

    public CustomerApiException(StatusRuntimeException exception) {
        super(exception.getMessage() + " > " + exception.getCause().getLocalizedMessage());
    }
}

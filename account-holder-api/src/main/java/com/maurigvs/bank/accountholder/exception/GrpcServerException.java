package com.maurigvs.bank.accountholder.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.NoSuchElementException;

public class GrpcServerException extends StatusRuntimeException {

    public GrpcServerException(NoSuchElementException exception) {
        super(Status.NOT_FOUND
                .withDescription(exception.getMessage()));
    }

    public GrpcServerException(RuntimeException exception) {
        super(Status.INTERNAL
                .withDescription(exception.getMessage())
                .withCause(exception));
    }
}

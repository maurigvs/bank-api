package com.maurigvs.bank.checkingaccount.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String key, String value) {
        super(entity + " not found by " + key + " " + value);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}

package com.maurigvs.bank.customer.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String key, String value) {
        super(entity + " not found by " + key + " " + value);
    }
}
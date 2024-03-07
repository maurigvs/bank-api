package com.maurigvs.bank.account.exception;

public class DateFormatException extends RuntimeException {

    public DateFormatException(String formatExpected) {
        super("Date must be in the format: " + formatExpected);
    }
}
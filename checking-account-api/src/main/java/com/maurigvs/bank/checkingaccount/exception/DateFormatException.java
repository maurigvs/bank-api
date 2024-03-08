package com.maurigvs.bank.checkingaccount.exception;

public class DateFormatException extends RuntimeException {

    public DateFormatException(String formatExpected) {
        super("Date must be in the format: " + formatExpected);
    }
}
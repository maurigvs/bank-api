package com.maurigvs.bank.accountholder.mapper;

import com.maurigvs.bank.accountholder.exception.DateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Function;

public class LocalDateMapper implements Function<String, LocalDate> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Override
    public LocalDate apply(String localDate) {
        try{
            return LocalDate.from(DATE_FORMATTER.parse(localDate));
        } catch (DateTimeParseException exception){
            throw new DateFormatException(DATE_FORMAT);
        }
    }

    public String reverse(LocalDate localDate){
        return localDate.format(DATE_FORMATTER);
    }
}
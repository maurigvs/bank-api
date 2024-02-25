package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.exception.DateFormatException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalDateMapperTest {

    @Test
    void should_return_LocalDate_given_an_String() {
        var localDate = "01/02/2024";
        var expected = LocalDate.of(2024,2,1);

        var result = new LocalDateMapper().apply(localDate);

        assertEquals(expected, result);
    }

    @Test
    void should_return_String_given_an_LocalDate() {
        var localDate = LocalDate.of(2024,2,1);
        var expected = "01/02/2024";

        var result = new LocalDateMapper().reverse(localDate);

        assertEquals(expected, result);
    }

    @Test
    void should_throw_DateFormatException_given_an_String_with_wrong_format() {
        var localDate = "2024-2-1";
        var expected = "Date must be in the format: dd/MM/yyyy";

        var exception = assertThrows(DateFormatException.class,
                () -> new LocalDateMapper().apply(localDate));

        assertEquals(expected, exception.getMessage());
    }
}

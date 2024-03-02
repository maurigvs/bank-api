package com.maurigvs.bank.transaction.mapper;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerMapperTest {

    @Test
    void should_return_Customer_given_Id() {
        var id = 1L;

        var result = new CustomerMapper().apply(id);

        assertEquals(id, result.getId());
        assertTrue(result.getAccountList().isEmpty());
    }
}
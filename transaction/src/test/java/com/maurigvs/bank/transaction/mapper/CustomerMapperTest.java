package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.model.Customer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.model.Customer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountMapperTest {

    @Test
    void should_return_Account_given_an_Customer_and_Id() {
        var id = 1L;
        var customer = new Customer(1L);

        var result = new AccountMapper(customer).apply(id);

        assertEquals(id, result.getId());
        assertSame(customer, result.getCustomer());
        assertTrue(result.getTransactionList().isEmpty());
    }
}
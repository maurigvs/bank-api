package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.model.Customer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionMapperTest {

    @Test
    void should_return_Transaction_given_an_TransactionRequest() {
        var account = new Account(1L, new Customer(1L));
        var request = new TransactionRequest(1L, 1L, "Initial deposit", 150.00);

        var result = new TransactionMapper(account).apply(request);

        assertNull(result.getId());
        assertNotNull(result.getDateTime());
        assertEquals(request.description(), result.getDescription());
        assertEquals(request.amount(), result.getAmount());
        assertSame(account, result.getAccount());
        assertTrue(result.getAccount().getTransactionList().contains(result));
    }

}
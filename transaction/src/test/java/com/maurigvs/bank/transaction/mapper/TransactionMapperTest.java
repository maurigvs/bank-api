package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionMapperTest {

    @Test
    void should_return_Transaction_given_an_TransactionRequest() {
        var request = new TransactionRequest(1L, 1L, "Initial deposit", 150.00);

        var result = new TransactionMapper().apply(request);

        assertNull(result.getId());
        assertNotNull(result.getDateTime());
        assertEquals(request.customerId(), result.getAccount().getCustomer().getId());
        assertEquals(request.accountId(), result.getAccount().getId());
        assertEquals(request.description(), result.getDescription());
        assertEquals(request.amount(), result.getAmount());
    }

}
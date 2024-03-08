package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.model.CheckingAccount;
import com.maurigvs.bank.transaction.model.AccountHolder;
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
        var checkingAccount = new CheckingAccount(1L, new AccountHolder(1L));
        var request = new TransactionRequest(1L, "Initial deposit", 150.00);

        var result = new TransactionMapper(checkingAccount).apply(request);

        assertNull(result.getId());
        assertNotNull(result.getDateTime());
        assertEquals(request.description(), result.getDescription());
        assertEquals(request.amount(), result.getAmount());
        assertSame(checkingAccount, result.getCheckingAccount());
        assertTrue(result.getCheckingAccount().getTransactionList().contains(result));
    }

}
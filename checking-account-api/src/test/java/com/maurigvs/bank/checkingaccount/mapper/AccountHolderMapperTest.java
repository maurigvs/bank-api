package com.maurigvs.bank.checkingaccount.mapper;

import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.FindAccountHolderReply;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountHolderMapperTest {

    @Test
    void should_return_AccountHolder_given_an_AccountHolderReply() {
        var expected = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
        var reply = FindAccountHolderReply.newBuilder().setAccountHolderData(expected).build();

        var result = new AccountHolderMapper().apply(reply);

        assertInstanceOf(AccountHolder.class, result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getTaxId(), result.getTaxId());
        assertTrue(result.getAccountList().isEmpty());
    }
}
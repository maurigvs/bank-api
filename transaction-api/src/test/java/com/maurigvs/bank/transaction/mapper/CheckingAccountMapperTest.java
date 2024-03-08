package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.grpc.CheckingAccountData;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.FindCheckingAccountReply;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CheckingAccountMapperTest {

    @Test
    void should_return_CheckingAccount_given_an_AccountHolder_and_Id() {
        var accountHolderData = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
        var checkingAccountData = CheckingAccountData.newBuilder().setId(1L).setBalance(150.00).setAccountHolderData(accountHolderData).build();
        var reply = FindCheckingAccountReply.newBuilder().setCheckingAccountData(checkingAccountData).build();

        var result = new CheckingAccountMapper().apply(reply);

        assertEquals(reply.getCheckingAccountData().getId(), result.getId());
        assertEquals(reply.getCheckingAccountData().getAccountHolderData().getId(), result.getAccountHolder().getId());
        assertTrue(result.getAccountHolder().getCheckingAccountList().contains(result));
    }
}
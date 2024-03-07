package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.grpc.AccountData;
import com.maurigvs.bank.grpc.CustomerData;
import com.maurigvs.bank.grpc.FindAccountReply;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountMapperTest {

    @Test
    void should_return_Account_given_an_Customer_and_Id() {
        var customerData = CustomerData.newBuilder().setId(1L).setTaxId("12345").build();
        var accountData = AccountData.newBuilder().setId(1L).setBalance(150.00).setCustomerData(customerData).build();
        var reply = FindAccountReply.newBuilder().setAccountData(accountData).build();

        var result = new AccountMapper().apply(reply);

        assertEquals(reply.getAccountData().getId(), result.getId());
        assertEquals(reply.getAccountData().getCustomerData().getId(), result.getCustomer().getId());
        assertTrue(result.getCustomer().getAccountList().contains(result));
    }
}
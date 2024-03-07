package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.model.Customer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConsumerAccountMapperTest {

    @Test
    void should_return_ConsumerAccount_given_an_AccountRequest() {
        var request = new AccountRequest("12345", 12345);
        var customer = new Customer(1L, "12345");
        var joinedAt = LocalDate.now();

        var result = new ConsumerAccountMapper(customer).apply(request);

        assertNull(result.getId());
        assertEquals(joinedAt, result.getJoinedAt());
        assertEquals(request.pinCode(), result.getPinCode());
        assertEquals(0.0, result.getBalance());
        assertSame(customer, result.getCustomer());
        assertTrue(result.getCustomer().getAccountList().contains(result));
    }
}

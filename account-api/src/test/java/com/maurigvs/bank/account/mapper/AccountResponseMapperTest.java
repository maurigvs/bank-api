package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.model.CommercialAccount;
import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.model.Customer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountResponseMapperTest {

    @Test
    void should_return_AccountResponse_given_an_CommercialAccount() {
        var customer = new Customer(1L, "12345");
        var account = new CommercialAccount(1L, LocalDate.now(), 12345, customer);
        var joinedAt = new LocalDateMapper().reverse(LocalDate.now());

        var result = new AccountResponseMapper<CommercialAccount>().apply(account);

        assertEquals(account.getId(), result.accountId());
        assertEquals(account.getCustomer().getTaxId(), result.taxId());
        assertEquals(joinedAt, result.joinedAt());
    }

    @Test
    void should_return_AccountResponse_given_an_ConsumerAccount() {
        var customer = new Customer(1L, "12345");
        var account = new ConsumerAccount(1L, LocalDate.now(), 12345, customer);
        var joinedAt = new LocalDateMapper().reverse(LocalDate.now());

        var result = new AccountResponseMapper<ConsumerAccount>().apply(account);

        assertEquals(account.getId(), result.accountId());
        assertEquals(account.getCustomer().getTaxId(), result.taxId());
        assertEquals(joinedAt, result.joinedAt());
        assertEquals(0.0, result.balance());
    }
}

package com.maurigvs.bank.checkingaccount.mapper;

import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountResponseMapperTest {

    @Test
    void should_return_AccountResponse_given_an_CheckingAccount() {
        var accountHolder = new AccountHolder(1L, "12345");
        var checkingAccount = new CheckingAccount(1L, LocalDate.now(), 12345, accountHolder);
        var joinedAt = new LocalDateMapper().reverse(LocalDate.now());

        var result = new AccountResponseMapper<CheckingAccount>().apply(checkingAccount);

        assertEquals(checkingAccount.getId(), result.accountId());
        assertEquals(checkingAccount.getCustomer().getTaxId(), result.taxId());
        assertEquals(joinedAt, result.joinedAt());
        assertEquals(0.0, result.balance());
    }
}

package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.dto.AccountRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConsumerAccountMapperTest {

    @Test
    void should_return_ConsumerAccount_given_an_AccountRequest() {
        var request = new AccountRequest("12345", 12345);
        var joinedAt = LocalDate.now();

        var result = new ConsumerAccountMapper().apply(request);

        assertNull(result.getId());
        assertEquals(request.taxId(), result.getTaxId());
        assertEquals(joinedAt, result.getJoinedAt());
    }
}
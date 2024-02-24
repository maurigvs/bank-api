package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.model.ConsumerAccount;

import java.time.LocalDate;
import java.util.function.Function;

public class ConsumerAccountMapper implements Function<AccountRequest, ConsumerAccount> {

    @Override
    public ConsumerAccount apply(AccountRequest request) {
        var joinedAt = LocalDate.now();

        return new ConsumerAccount(null, request.taxId(), joinedAt);
    }
}

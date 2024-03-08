package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.model.Account;

import java.util.function.Function;

public class AccountResponseMapper<T extends Account> implements Function<T, AccountResponse> {

    @Override
    public AccountResponse apply(T t) {
        var joinedAt = new LocalDateMapper().reverse(t.getJoinedAt());

        return new AccountResponse(t.getId(), t.getCustomer().getTaxId(), joinedAt, t.getBalance());
    }
}

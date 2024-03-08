package com.maurigvs.bank.checkingaccount.mapper;

import com.maurigvs.bank.checkingaccount.dto.AccountResponse;
import com.maurigvs.bank.checkingaccount.model.Account;

import java.util.function.Function;

public class AccountResponseMapper<T extends Account> implements Function<T, AccountResponse> {

    @Override
    public AccountResponse apply(T t) {
        var joinedAt = new LocalDateMapper().reverse(t.getJoinedAt());

        return new AccountResponse(t.getId(), t.getCustomer().getTaxId(), joinedAt, t.getBalance());
    }
}

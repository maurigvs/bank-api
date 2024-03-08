package com.maurigvs.bank.checkingaccount.mapper;

import com.maurigvs.bank.checkingaccount.dto.AccountRequest;
import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.checkingaccount.model.CheckingAccount;

import java.time.LocalDate;
import java.util.function.Function;

public class CheckingAccountMapper implements Function<AccountRequest, CheckingAccount> {

    private final AccountHolder accountHolder;

    public CheckingAccountMapper(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    @Override
    public CheckingAccount apply(AccountRequest request) {
        var joinedAt = LocalDate.now();
        var checkingAccount = new CheckingAccount(null, joinedAt, request.pinCode(), accountHolder);
        accountHolder.getAccountList().add(checkingAccount);

        return checkingAccount;
    }
}

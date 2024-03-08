package com.maurigvs.bank.checkingaccount.mapper;

import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.grpc.FindAccountHolderReply;

import java.util.function.Function;

public class AccountHolderMapper implements Function<FindAccountHolderReply, AccountHolder> {

    @Override
    public AccountHolder apply(FindAccountHolderReply reply) {
        var accountHolderData = reply.getAccountHolderData();

        return new AccountHolder(accountHolderData.getId(), accountHolderData.getTaxId());
    }
}

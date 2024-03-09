package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.grpc.FindCheckingAccountReply;
import com.maurigvs.bank.transaction.model.AccountHolder;
import com.maurigvs.bank.transaction.model.CheckingAccount;

import java.util.function.Function;

public class CheckingAccountMapper implements Function<FindCheckingAccountReply, CheckingAccount> {

    @Override
    public CheckingAccount apply(FindCheckingAccountReply reply) {
        var accountHolder = new AccountHolder(reply.getCheckingAccountData().getAccountHolderData().getId());
        var checkingAccount = new CheckingAccount(reply.getCheckingAccountData().getId(), accountHolder);
        accountHolder.getCheckingAccountList().add(checkingAccount);

        return checkingAccount;
    }
}

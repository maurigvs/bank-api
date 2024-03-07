package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.grpc.FindAccountReply;
import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.model.Customer;

import java.util.function.Function;

public class AccountMapper implements Function<FindAccountReply, Account> {

    @Override
    public Account apply(FindAccountReply reply) {
        var customer = new Customer(reply.getAccountData().getCustomerData().getId());
        var account = new Account(reply.getAccountData().getId(), customer);
        customer.getAccountList().add(account);

        return account;
    }
}

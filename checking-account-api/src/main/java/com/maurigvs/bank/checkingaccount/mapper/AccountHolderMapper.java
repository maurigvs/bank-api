package com.maurigvs.bank.checkingaccount.mapper;

import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.grpc.FindCustomerReply;

import java.util.function.Function;

public class AccountHolderMapper implements Function<FindCustomerReply, AccountHolder> {

    @Override
    public AccountHolder apply(FindCustomerReply reply) {
        var customerData = reply.getCustomerData();

        return new AccountHolder(customerData.getId(), customerData.getTaxId());
    }
}

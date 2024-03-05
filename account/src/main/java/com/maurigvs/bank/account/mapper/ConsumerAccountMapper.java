package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.model.Customer;

import java.time.LocalDate;
import java.util.function.Function;

public class ConsumerAccountMapper implements Function<AccountRequest, ConsumerAccount> {

    private final Customer customer;

    public ConsumerAccountMapper(Customer customer) {
        this.customer = customer;
    }

    @Override
    public ConsumerAccount apply(AccountRequest request) {
        var joinedAt = LocalDate.now();
        var consumerAccount = new ConsumerAccount(null, joinedAt, request.pinCode(), customer);
        customer.getAccountList().add(consumerAccount);

        return consumerAccount;
    }
}

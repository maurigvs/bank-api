package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.model.Customer;

import java.util.function.Function;

public class AccountMapper implements Function<Long, Account> {

    private final Customer customer;

    public AccountMapper(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Account apply(Long id) {
        return new Account(id, customer);
    }
}

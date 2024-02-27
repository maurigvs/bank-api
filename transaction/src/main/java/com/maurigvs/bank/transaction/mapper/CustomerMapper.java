package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.model.Customer;

import java.util.function.Function;

public class CustomerMapper implements Function<Long, Customer> {

    @Override
    public Customer apply(Long id) {
        return new Customer(id);
    }
}

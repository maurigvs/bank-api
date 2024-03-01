package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.CustomerReply;
import com.maurigvs.bank.account.model.Customer;

import java.util.function.Function;

public class CustomerMapper implements Function<CustomerReply, Customer> {

    @Override
    public Customer apply(CustomerReply customerReply) {
        return new Customer(
                customerReply.getId(),
                customerReply.getTaxId());
    }
}

package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.grpc.FindCustomerReply;

import java.util.function.Function;

public class CustomerMapper implements Function<FindCustomerReply, Customer> {

    @Override
    public Customer apply(FindCustomerReply reply) {
        var customerData = reply.getCustomerData();
        return new Customer(
                customerData.getId(),
                customerData.getTaxId());
    }
}

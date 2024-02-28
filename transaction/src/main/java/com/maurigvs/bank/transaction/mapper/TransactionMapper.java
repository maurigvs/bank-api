package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.function.Function;

public class TransactionMapper implements Function<TransactionRequest, Transaction> {

    @Override
    public Transaction apply(TransactionRequest request) {
        var dateTime = LocalDateTime.now();
        var customer = new CustomerMapper().apply(request.customerId());
        var account = new AccountMapper(customer).apply(request.accountId());

        return new Transaction(null, dateTime, request.description(), request.amount(), account);
    }
}

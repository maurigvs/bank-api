package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.model.CheckingAccount;
import com.maurigvs.bank.transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.function.Function;

public class TransactionMapper implements Function<TransactionRequest, Transaction> {

    private final CheckingAccount checkingAccount;

    public TransactionMapper(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    @Override
    public Transaction apply(TransactionRequest request) {
        var dateTime = LocalDateTime.now();
        var transaction = new Transaction(null, dateTime, request.description(), request.amount(), checkingAccount);
        checkingAccount.getTransactionList().add(transaction);

        return transaction;
    }
}

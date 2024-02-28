package com.maurigvs.bank.transaction.mapper;

import com.maurigvs.bank.transaction.dto.TransactionResponse;
import com.maurigvs.bank.transaction.model.Transaction;

import java.util.function.Function;

public class TransactionResponseMapper implements Function<Transaction, TransactionResponse> {
    @Override
    public TransactionResponse apply(Transaction transaction) {
        var dateTime = new LocalDateTimeMapper().apply(transaction.getDateTime());

        return new TransactionResponse(dateTime,
                transaction.getDescription(),
                transaction.getId(),
                transaction.getAmount());
    }
}

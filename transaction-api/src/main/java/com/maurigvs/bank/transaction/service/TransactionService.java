package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {

    void create(Transaction transaction);

    List<Transaction> findByCheckingAccountId(Long checkingAccountId);
}

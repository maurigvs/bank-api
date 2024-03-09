package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Transaction transaction) {
        repository.save(transaction);
    }
}

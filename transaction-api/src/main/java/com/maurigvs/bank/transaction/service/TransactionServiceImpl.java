package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.repository.AccountRepository;
import com.maurigvs.bank.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void create(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        var account = accountRepository.findById(accountId).orElseThrow();
        return account.getTransactionList();
    }
}

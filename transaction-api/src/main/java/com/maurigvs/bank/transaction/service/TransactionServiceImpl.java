package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.repository.CheckingAccountRepository;
import com.maurigvs.bank.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CheckingAccountRepository checkingAccountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CheckingAccountRepository checkingAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.checkingAccountRepository = checkingAccountRepository;
    }

    @Override
    public void create(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findByCheckingAccountId(Long checkingAccountId) {
        var checkingAccount = checkingAccountRepository.findById(checkingAccountId).orElseThrow();
        return checkingAccount.getTransactionList();
    }
}

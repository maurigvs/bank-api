package com.maurigvs.bank.checkingaccount.service;

import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import com.maurigvs.bank.checkingaccount.repository.CheckingAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckingAccountService implements AccountService<CheckingAccount> {

    private final CheckingAccountRepository repository;

    public CheckingAccountService(CheckingAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void openAccount(CheckingAccount account) {
        repository.save(account);
    }

    @Override
    public List<CheckingAccount> findAllAccounts() {
        return repository.findAll();
    }

    @Override
    public CheckingAccount findById(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Account", "Id", String.valueOf(id))
        );
    }

    @Override
    public CheckingAccount updateBalanceById(Long id, Double amount) {
        var account = repository.getReferenceById(id);
        var balance = account.getBalance() + amount;
        account.setBalance(balance);
        return account;
    }

    @Override
    public void closeAccount(Long id) {
        var checkingAccount = findById(id);
        repository.delete(checkingAccount);
    }
}

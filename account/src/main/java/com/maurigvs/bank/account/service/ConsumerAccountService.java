package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.repository.ConsumerAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerAccountService implements AccountService<ConsumerAccount> {

    private final ConsumerAccountRepository repository;

    public ConsumerAccountService(ConsumerAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void openAccount(ConsumerAccount account) {
        repository.save(account);
    }

    @Override
    public List<ConsumerAccount> findAllAccounts() {
        return repository.findAll();
    }

    @Override
    public void updateAccount(Long id, ConsumerAccount update) {
        var account = findById(id);
        repository.save(account);
    }

    @Override
    public void closeAccount(Long id) {
        var account = findById(id);
        repository.delete(account);
    }

    private ConsumerAccount findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}

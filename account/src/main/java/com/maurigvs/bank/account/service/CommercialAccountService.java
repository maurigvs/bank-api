package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.exception.EntityNotFoundException;
import com.maurigvs.bank.account.model.CommercialAccount;
import com.maurigvs.bank.account.repository.CommercialAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommercialAccountService implements AccountService<CommercialAccount> {

    private final CommercialAccountRepository repository;

    public CommercialAccountService(CommercialAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void openAccount(CommercialAccount account) {
        repository.save(account);
    }

    @Override
    public List<CommercialAccount> findAllAccounts() {
        return repository.findAll();
    }

    @Override
    public CommercialAccount findById(Long id){
        return repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Account", "Id", String.valueOf(id))
        );
    }

    @Override
    public void closeAccount(Long id) {
        var account = findById(id);
        repository.delete(account);
    }
}

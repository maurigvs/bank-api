package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.grpc.AccountGrpcClient;
import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountGrpcClient accountGrpcClient;

    public AccountService(AccountRepository accountRepository,
                          AccountGrpcClient accountGrpcClient) {
        this.accountRepository = accountRepository;
        this.accountGrpcClient = accountGrpcClient;
    }

    public Account findById(Long id){
        return accountRepository.findById(id).orElseGet(
            () -> accountRepository.save(
                    accountGrpcClient.findById(id)));
    }

    public void updateBalance(Long id, Double amount){
        accountGrpcClient.updateBalance(id, amount);
    }
}

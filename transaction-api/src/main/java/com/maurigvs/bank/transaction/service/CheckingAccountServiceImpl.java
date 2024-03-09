package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.grpc.client.CheckingAccountGrpcClient;
import com.maurigvs.bank.transaction.model.CheckingAccount;
import com.maurigvs.bank.transaction.repository.CheckingAccountRepository;
import org.springframework.stereotype.Service;

@Service
class CheckingAccountServiceImpl implements CheckingAccountService {

    private final CheckingAccountRepository repository;
    private final CheckingAccountGrpcClient grpcClient;

    public CheckingAccountServiceImpl(CheckingAccountRepository repository,
                                      CheckingAccountGrpcClient grpcClient) {
        this.repository = repository;
        this.grpcClient = grpcClient;
    }

    @Override
    public CheckingAccount findById(Long id) {
        return repository.findById(id).orElseGet(
            () -> repository.save(grpcClient.findById(id)));
    }
}

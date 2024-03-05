package com.maurigvs.bank.transaction.grpc;

import com.maurigvs.bank.grpc.AccountServiceGrpc;
import com.maurigvs.bank.grpc.FindAccountRequest;
import com.maurigvs.bank.transaction.exception.AccountApiException;
import com.maurigvs.bank.transaction.exception.EntityNotFoundException;
import com.maurigvs.bank.transaction.mapper.AccountMapper;
import com.maurigvs.bank.transaction.model.Account;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class AccountApiService {

    private final AccountServiceGrpc.AccountServiceBlockingStub accountGrpcService;

    public AccountApiService(AccountServiceGrpc.AccountServiceBlockingStub accountGrpcService) {
        this.accountGrpcService = accountGrpcService;
    }

    public Account findById(Long id){
        try {
            var request = FindAccountRequest.newBuilder().setId(id).build();
            var reply = accountGrpcService.findById(request);
            return new AccountMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new AccountApiException(exception);
        }
    }
}

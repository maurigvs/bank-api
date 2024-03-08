package com.maurigvs.bank.transaction.grpc;

import com.maurigvs.bank.grpc.AccountServiceGrpc;
import com.maurigvs.bank.grpc.FindAccountRequest;
import com.maurigvs.bank.grpc.UpdateBalanceRequest;
import com.maurigvs.bank.transaction.exception.AccountApiException;
import com.maurigvs.bank.transaction.exception.EntityNotFoundException;
import com.maurigvs.bank.transaction.mapper.AccountMapper;
import com.maurigvs.bank.transaction.model.Account;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class AccountGrpcClient {

    private final AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub;

    public AccountGrpcClient(AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub) {
        this.accountServiceBlockingStub = accountServiceBlockingStub;
    }

    public Account findById(Long id){
        try {
            var request = FindAccountRequest.newBuilder().setId(id).build();
            var reply = accountServiceBlockingStub.findById(request);
            return new AccountMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new AccountApiException(exception);
        }
    }

    public void updateBalance(Long id, double amount){
        try{
            var request = UpdateBalanceRequest.newBuilder()
                    .setAccountId(id)
                    .setTransactionAmount(amount)
                    .build();
            accountServiceBlockingStub.updateBalance(request);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new AccountApiException(exception);
        }
    }
}

package com.maurigvs.bank.checkingaccount.grpc.client;

import com.maurigvs.bank.checkingaccount.exception.AccountHolderApiException;
import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.checkingaccount.mapper.AccountHolderMapper;
import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.grpc.AccountHolderServiceGrpc;
import com.maurigvs.bank.grpc.FindAccountHolderRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderGrpcClient {

    private final AccountHolderServiceGrpc.AccountHolderServiceBlockingStub blockingStub;

    public AccountHolderGrpcClient(AccountHolderServiceGrpc.AccountHolderServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public AccountHolder findByTaxId(String taxId){
        try{
            var request = FindAccountHolderRequest.newBuilder().setTaxId(taxId).build();
            var reply = blockingStub.findByTaxId(request);
            return new AccountHolderMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new AccountHolderApiException(exception);
        }
    }
}

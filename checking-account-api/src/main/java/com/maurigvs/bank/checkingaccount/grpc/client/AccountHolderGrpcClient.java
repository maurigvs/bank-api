package com.maurigvs.bank.checkingaccount.grpc.client;

import com.maurigvs.bank.checkingaccount.exception.AccountHolderApiException;
import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.checkingaccount.mapper.AccountHolderMapper;
import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.grpc.CustomerServiceGrpc;
import com.maurigvs.bank.grpc.FindCustomerRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderGrpcClient {

    private final CustomerServiceGrpc.CustomerServiceBlockingStub blockingStub;

    public AccountHolderGrpcClient(CustomerServiceGrpc.CustomerServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public AccountHolder findByTaxId(String taxId){
        try{
            var request = FindCustomerRequest.newBuilder().setTaxId(taxId).build();
            var reply = blockingStub.findByTaxId(request);
            return new AccountHolderMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new AccountHolderApiException(exception);
        }
    }
}

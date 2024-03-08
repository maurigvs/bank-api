package com.maurigvs.bank.transaction.grpc.client;

import com.maurigvs.bank.grpc.CheckingAccountServiceGrpc;
import com.maurigvs.bank.grpc.FindCheckingAccountRequest;
import com.maurigvs.bank.transaction.exception.CheckingAccountApiException;
import com.maurigvs.bank.transaction.exception.EntityNotFoundException;
import com.maurigvs.bank.transaction.mapper.CheckingAccountMapper;
import com.maurigvs.bank.transaction.model.CheckingAccount;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class CheckingAccountGrpcClient {

    private final CheckingAccountServiceGrpc.CheckingAccountServiceBlockingStub blockingStub;

    public CheckingAccountGrpcClient(CheckingAccountServiceGrpc.CheckingAccountServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public CheckingAccount findById(Long id){
        try {
            var request = FindCheckingAccountRequest.newBuilder().setId(id).build();
            var reply = blockingStub.findById(request);
            return new CheckingAccountMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new CheckingAccountApiException(exception);
        }
    }
}

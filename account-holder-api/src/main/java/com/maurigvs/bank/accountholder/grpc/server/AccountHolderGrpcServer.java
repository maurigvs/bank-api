package com.maurigvs.bank.accountholder.grpc.server;

import com.maurigvs.bank.accountholder.grpc.server.calls.FindByTaxIdGrpcCall;
import com.maurigvs.bank.grpc.AccountHolderServiceGrpc;
import com.maurigvs.bank.grpc.FindAccountHolderReply;
import com.maurigvs.bank.grpc.FindAccountHolderRequest;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderGrpcServer extends AccountHolderServiceGrpc.AccountHolderServiceImplBase {

    private final FindByTaxIdGrpcCall findByTaxIdGrpcCall;

    public AccountHolderGrpcServer(FindByTaxIdGrpcCall findByTaxIdGrpcCall) {
        this.findByTaxIdGrpcCall = findByTaxIdGrpcCall;
    }

    @Override
    public void findByTaxId(FindAccountHolderRequest request,
                            StreamObserver<FindAccountHolderReply> observer) {
        try{
            observer.onNext(findByTaxIdGrpcCall.processCall(request));

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();
    }
}

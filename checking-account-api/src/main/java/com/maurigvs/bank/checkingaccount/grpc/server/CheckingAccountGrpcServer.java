package com.maurigvs.bank.checkingaccount.grpc.server;

import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import com.maurigvs.bank.checkingaccount.service.CheckingAccountService;
import com.maurigvs.bank.grpc.AccountData;
import com.maurigvs.bank.grpc.AccountServiceGrpc;
import com.maurigvs.bank.grpc.CustomerData;
import com.maurigvs.bank.grpc.FindAccountReply;
import com.maurigvs.bank.grpc.FindAccountRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CheckingAccountGrpcServer extends AccountServiceGrpc.AccountServiceImplBase {

    private final CheckingAccountService service;

    public CheckingAccountGrpcServer(CheckingAccountService service) {
        this.service = service;
    }

    @Override
    public void findById(FindAccountRequest request, StreamObserver<FindAccountReply> observer) {
        try{
            var reply = findById(request);
            observer.onNext(reply);

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();
    }

    private FindAccountReply findById(FindAccountRequest request) {
        try{
            var account = service.findById(request.getId());
            return new FindAccountReplyMapper().apply(account);

        } catch (EntityNotFoundException ex){
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription(ex.getMessage()));

        } catch (RuntimeException ex){
            throw new StatusRuntimeException(Status.INTERNAL.withCause(ex));
        }
    }

    public static class FindAccountReplyMapper implements Function<CheckingAccount, FindAccountReply> {

        @Override
        public FindAccountReply apply(CheckingAccount account) {

            var customerData = CustomerData.newBuilder()
                    .setId(account.getCustomer().getId())
                    .setTaxId(account.getCustomer().getTaxId())
                    .build();

            var accountData = AccountData.newBuilder()
                    .setId(account.getId())
                    .setBalance(account.getBalance())
                    .setCustomerData(customerData)
                    .build();

            return FindAccountReply.newBuilder()
                    .setAccountData(accountData).build();
        }
    }
}

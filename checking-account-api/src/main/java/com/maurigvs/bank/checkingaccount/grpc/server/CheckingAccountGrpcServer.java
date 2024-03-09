package com.maurigvs.bank.checkingaccount.grpc.server;

import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import com.maurigvs.bank.checkingaccount.service.CheckingAccountService;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.CheckingAccountData;
import com.maurigvs.bank.grpc.CheckingAccountServiceGrpc;
import com.maurigvs.bank.grpc.FindCheckingAccountReply;
import com.maurigvs.bank.grpc.FindCheckingAccountRequest;
import com.maurigvs.bank.grpc.UpdateBalanceReply;
import com.maurigvs.bank.grpc.UpdateBalanceRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CheckingAccountGrpcServer extends CheckingAccountServiceGrpc.CheckingAccountServiceImplBase {

    private final CheckingAccountService service;

    public CheckingAccountGrpcServer(CheckingAccountService service) {
        this.service = service;
    }

    @Override
    public void findById(FindCheckingAccountRequest findRequest,
                         StreamObserver<FindCheckingAccountReply> observer) {
        try{
            var findReply = processFindById(findRequest);
            observer.onNext(findReply);

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();
    }

    @Override
    public void updateBalanceById(UpdateBalanceRequest updateRequest,
                                  StreamObserver<UpdateBalanceReply> observer) {
        try{
            var updateReply = processUpdateBalanceById(updateRequest);
            observer.onNext(updateReply);

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();
    }

    private FindCheckingAccountReply processFindById(FindCheckingAccountRequest request) {
        try{
            var checkingAccount = service.findById(request.getId());
            return new FindCheckingAccountReplyMapper().apply(checkingAccount);

        } catch (EntityNotFoundException ex){
            throw new StatusRuntimeException(Status.NOT_FOUND.withDescription(ex.getMessage()));

        } catch (RuntimeException ex){
            throw new StatusRuntimeException(Status.INTERNAL.withCause(ex));
        }
    }

    private UpdateBalanceReply processUpdateBalanceById(UpdateBalanceRequest request) {
        try{
            var account = service.updateBalanceById(
                    request.getId(), request.getAmount());

            return UpdateBalanceReply.newBuilder()
                    .setId(account.getId())
                    .setBalance(account.getBalance())
                    .build();

        } catch (EntityNotFoundException ex){
            throw new StatusRuntimeException(Status.NOT_FOUND.withDescription(ex.getMessage()));

        } catch (RuntimeException ex){
            throw new StatusRuntimeException(Status.INTERNAL.withCause(ex));
        }
    }

    public static class FindCheckingAccountReplyMapper implements Function<CheckingAccount, FindCheckingAccountReply> {

        @Override
        public FindCheckingAccountReply apply(CheckingAccount account) {

            var accountHolderData = AccountHolderData.newBuilder()
                    .setId(account.getAccountHolder().getId())
                    .setTaxId(account.getAccountHolder().getTaxId())
                    .build();

            var checkingAccountData = CheckingAccountData.newBuilder()
                    .setId(account.getId())
                    .setBalance(account.getBalance())
                    .setAccountHolderData(accountHolderData)
                    .build();

            return FindCheckingAccountReply.newBuilder()
                    .setCheckingAccountData(checkingAccountData)
                    .build();
        }
    }
}

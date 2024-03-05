package com.maurigvs.bank.account.grpc;

import com.maurigvs.bank.account.exception.EntityNotFoundException;
import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.service.ConsumerAccountService;
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
public class AccountGrpcServer extends AccountServiceGrpc.AccountServiceImplBase {

    private final ConsumerAccountService consumerAccountService;

    public AccountGrpcServer(ConsumerAccountService consumerAccountService) {
        this.consumerAccountService = consumerAccountService;
    }

    @Override
    public void findById(FindAccountRequest request, StreamObserver<FindAccountReply> observer) {
        try{
            var reply = findById(request);
            observer.onNext(reply);

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();;
    }

    private FindAccountReply findById(FindAccountRequest request) {
        try{
            var account = consumerAccountService.findById(request.getId());
            return new FindAccountReplyMapper().apply(account);

        } catch (EntityNotFoundException ex){
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription(ex.getMessage()));

        } catch (RuntimeException ex){
            throw new StatusRuntimeException(Status.INTERNAL.withCause(ex));
        }
    }

    public static class FindAccountReplyMapper implements Function<ConsumerAccount, FindAccountReply> {

        @Override
        public FindAccountReply apply(ConsumerAccount account) {

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

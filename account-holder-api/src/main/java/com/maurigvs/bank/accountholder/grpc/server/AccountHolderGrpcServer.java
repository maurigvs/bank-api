package com.maurigvs.bank.accountholder.grpc.server;

import com.maurigvs.bank.accountholder.exception.EntityNotFoundException;
import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.service.PersonService;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.AccountHolderServiceGrpc;
import com.maurigvs.bank.grpc.FindAccountHolderReply;
import com.maurigvs.bank.grpc.FindAccountHolderRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountHolderGrpcServer extends AccountHolderServiceGrpc.AccountHolderServiceImplBase {

    private final PersonService personService;

    public AccountHolderGrpcServer(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void findByTaxId(FindAccountHolderRequest request,
                            StreamObserver<FindAccountHolderReply> observer) {
        try{
            var reply = findByTaxId(request);
            observer.onNext(reply);

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();
    }

    private FindAccountHolderReply findByTaxId(FindAccountHolderRequest request) {
        try {
            var person = personService.findByTaxId(request.getTaxId());
            return new FindAccountHolderReplyMapper().apply(person);

        } catch (EntityNotFoundException ex){
            throw new StatusRuntimeException(Status.NOT_FOUND.withDescription(ex.getMessage()));

        } catch (RuntimeException ex){
            throw new StatusRuntimeException(Status.INTERNAL.withCause(ex));
        }
    }

    public static class FindAccountHolderReplyMapper implements Function<Person, FindAccountHolderReply> {

        @Override
        public FindAccountHolderReply apply(Person person) {
            var accountHolder = AccountHolderData.newBuilder()
                    .setId(person.getId())
                    .setTaxId(person.getTaxId())
                    .build();

            return FindAccountHolderReply.newBuilder()
                    .setAccountHolderData(accountHolder)
                    .build();
        }
    }
}

package com.maurigvs.bank.accountholder.grpc.server;

import com.maurigvs.bank.accountholder.exception.EntityNotFoundException;
import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.service.PersonService;
import com.maurigvs.bank.grpc.CustomerData;
import com.maurigvs.bank.grpc.CustomerServiceGrpc;
import com.maurigvs.bank.grpc.FindCustomerReply;
import com.maurigvs.bank.grpc.FindCustomerRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountHolderGrpcServer extends CustomerServiceGrpc.CustomerServiceImplBase {

    private final PersonService personService;

    public AccountHolderGrpcServer(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void findByTaxId(FindCustomerRequest request,
                            StreamObserver<FindCustomerReply> observer) {
        try{
            var reply = findByTaxId(request);
            observer.onNext(reply);

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();;
    }

    private FindCustomerReply findByTaxId(FindCustomerRequest request) {
        try {
            var person = personService.findByTaxId(request.getTaxId());
            return new FindCustomerReplyMapper().apply(person);

        } catch (EntityNotFoundException ex){
            throw new StatusRuntimeException(Status.NOT_FOUND.withDescription(ex.getMessage()));

        } catch (RuntimeException ex){
            throw new StatusRuntimeException(Status.INTERNAL.withCause(ex));
        }
    }

    public static class FindCustomerReplyMapper implements Function<Person, FindCustomerReply> {

        @Override
        public FindCustomerReply apply(Person person) {
            var accountHolder = CustomerData.newBuilder()
                    .setId(person.getId())
                    .setTaxId(person.getTaxId())
                    .build();

            return FindCustomerReply.newBuilder()
                    .setCustomerData(accountHolder)
                    .build();
        }
    }
}

package com.maurigvs.bank.customer.grpc;

import com.maurigvs.bank.CustomerGrpcGrpc;
import com.maurigvs.bank.CustomerReply;
import com.maurigvs.bank.CustomerRequest;
import com.maurigvs.bank.customer.exception.EntityNotFoundException;
import com.maurigvs.bank.customer.model.Person;
import com.maurigvs.bank.customer.service.PersonService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerGrpcService extends CustomerGrpcGrpc.CustomerGrpcImplBase {

    private final PersonService personService;

    public CustomerGrpcService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void findByTaxId(CustomerRequest request, StreamObserver<CustomerReply> observer) {
        try{
            var customerReply = findByTaxId(request);
            observer.onNext(customerReply);

        } catch (StatusRuntimeException exception){
            observer.onError(exception);
        }
        observer.onCompleted();;
    }

    private CustomerReply findByTaxId(CustomerRequest request) {
        try {
            var person = personService.findByTaxId(request.getTaxId());
            return new CustomerReplyMapper().apply(person);

        } catch (EntityNotFoundException ex){
            throw new StatusRuntimeException(
                    Status.NOT_FOUND.withDescription(ex.getMessage()));

        } catch (RuntimeException ex){
            throw new StatusRuntimeException(
                    Status.INTERNAL.withCause(ex));
        }
    }

    static class CustomerReplyMapper implements Function<Person, CustomerReply> {

        @Override
        public CustomerReply apply(Person person) {
            return CustomerReply.newBuilder()
                    .setId(person.getId())
                    .setTaxId(person.getTaxId())
                    .build();
        }
    }
}

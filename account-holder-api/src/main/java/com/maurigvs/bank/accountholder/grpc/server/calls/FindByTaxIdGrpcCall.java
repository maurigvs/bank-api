package com.maurigvs.bank.accountholder.grpc.server.calls;

import com.maurigvs.bank.accountholder.exception.GrpcServerException;
import com.maurigvs.bank.accountholder.service.PersonService;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.FindAccountHolderReply;
import com.maurigvs.bank.grpc.FindAccountHolderRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FindByTaxIdGrpcCall implements GrpcCall<FindAccountHolderRequest, FindAccountHolderReply> {

    private final PersonService personService;

    public FindByTaxIdGrpcCall(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public FindAccountHolderReply processCall(FindAccountHolderRequest request) {
        try {
            var person = personService.findByTaxId(request.getTaxId());

            var accountHolder = AccountHolderData.newBuilder()
                    .setId(person.getId())
                    .setTaxId(person.getTaxId())
                    .build();

            return FindAccountHolderReply.newBuilder()
                    .setAccountHolderData(accountHolder)
                    .build();

        } catch (NoSuchElementException exception){
            throw new GrpcServerException(exception);

        } catch (RuntimeException exception){
            throw new GrpcServerException(exception);
        }
    }
}

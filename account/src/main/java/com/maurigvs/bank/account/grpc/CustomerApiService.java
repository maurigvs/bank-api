package com.maurigvs.bank.account.grpc;

import com.maurigvs.bank.CustomerGrpcGrpc;
import com.maurigvs.bank.CustomerRequest;
import com.maurigvs.bank.account.exception.CustomerApiException;
import com.maurigvs.bank.account.exception.EntityNotFoundException;
import com.maurigvs.bank.account.mapper.CustomerMapper;
import com.maurigvs.bank.account.model.Customer;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class CustomerApiService {

    private final CustomerGrpcGrpc.CustomerGrpcBlockingStub customerGrpcBlockingStub;

    public CustomerApiService(CustomerGrpcGrpc.CustomerGrpcBlockingStub customerGrpcBlockingStub) {
        this.customerGrpcBlockingStub = customerGrpcBlockingStub;
    }

    public Customer findByTaxId(String taxId){
        try{
            var request = CustomerRequest.newBuilder().setTaxId(taxId).build();
            var reply = customerGrpcBlockingStub.findByTaxId(request);
            return new CustomerMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new CustomerApiException(exception);
        }
    }
}

package com.maurigvs.bank.account.grpc;

import com.maurigvs.bank.account.exception.CustomerApiException;
import com.maurigvs.bank.account.exception.EntityNotFoundException;
import com.maurigvs.bank.account.mapper.CustomerMapper;
import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.grpc.CustomerServiceGrpc;
import com.maurigvs.bank.grpc.FindCustomerRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class CustomerGrpcClient {

    private final CustomerServiceGrpc.CustomerServiceBlockingStub customerGrpcStub;

    public CustomerGrpcClient(CustomerServiceGrpc.CustomerServiceBlockingStub customerGrpcStub) {
        this.customerGrpcStub = customerGrpcStub;
    }

    public Customer findByTaxId(String taxId){
        try{
            var request = FindCustomerRequest.newBuilder().setTaxId(taxId).build();
            var reply = customerGrpcStub.findByTaxId(request);
            return new CustomerMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new CustomerApiException(exception);
        }
    }
}

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

    private final CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub;

    public CustomerGrpcClient(CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub) {
        this.customerServiceBlockingStub = customerServiceBlockingStub;
    }

    public Customer findByTaxId(String taxId){
        try{
            var request = FindCustomerRequest.newBuilder().setTaxId(taxId).build();
            var reply = customerServiceBlockingStub.findByTaxId(request);
            return new CustomerMapper().apply(reply);

        } catch (StatusRuntimeException exception){
            if(Status.NOT_FOUND.getCode().equals(exception.getStatus().getCode()))
                throw new EntityNotFoundException(exception.getStatus().getDescription());

            throw new CustomerApiException(exception);
        }
    }
}

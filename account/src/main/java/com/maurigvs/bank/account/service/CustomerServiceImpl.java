package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.grpc.CustomerGrpcClient;
import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerGrpcClient customerGrpcClient;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerGrpcClient customerGrpcClient) {
        this.customerRepository = customerRepository;
        this.customerGrpcClient = customerGrpcClient;
    }

    @Override
    public Customer findByTaxId(String taxId) {
        var customer = customerGrpcClient.findByTaxId(taxId);
        customerRepository.save(customer);
        return customer;
    }
}

package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.account.grpc.CustomerApiService;
import com.maurigvs.bank.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerApiService customerApiService;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerApiService customerApiService) {
        this.customerRepository = customerRepository;
        this.customerApiService = customerApiService;
    }

    @Override
    public Customer findByTaxId(String taxId) {
        var customer = customerApiService.findByTaxId(taxId);
        customerRepository.save(customer);
        return customer;
    }
}

package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    // TODO Replace with integration with grpc call to Customer Service
    @Override
    public Customer findByTaxId(String taxId) {
        if(!repository.existsByTaxId(taxId))
            repository.save(new Customer(null, taxId));

        return repository.findByTaxId(taxId).orElseThrow();
    }
}

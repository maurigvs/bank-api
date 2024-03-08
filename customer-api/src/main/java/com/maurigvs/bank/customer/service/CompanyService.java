package com.maurigvs.bank.customer.service;

import com.maurigvs.bank.customer.exception.EntityNotFoundException;
import com.maurigvs.bank.customer.model.Company;
import com.maurigvs.bank.customer.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements CustomerService<Company> {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Company customer) {
        repository.save(customer);
    }

    @Override
    public Company findByTaxId(String taxId) {
        return repository.findByTaxId(taxId).orElseThrow(
            () -> new EntityNotFoundException("Company", "taxId", taxId)
        );
    }

    @Override
    public List<Company> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        var customer = repository.findById(id).orElseThrow();
        repository.delete(customer);
    }
}

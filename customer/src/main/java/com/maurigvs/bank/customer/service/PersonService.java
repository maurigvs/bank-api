package com.maurigvs.bank.customer.service;

import com.maurigvs.bank.customer.exception.EntityNotFoundException;
import com.maurigvs.bank.customer.model.Person;
import com.maurigvs.bank.customer.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements CustomerService<Person> {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Person customer) {
        repository.save(customer);
    }

    @Override
    public Person findByTaxId(String taxId) {
        return repository.findByTaxId(taxId).orElseThrow(
            () -> new EntityNotFoundException("Person", "taxId", taxId)
        );
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        var customer = repository.findById(id).orElseThrow();
        repository.delete(customer);
    }
}

package com.maurigvs.bank.accountholder.service;

import com.maurigvs.bank.accountholder.exception.EntityNotFoundException;
import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements AccountHolderService<Person> {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Person person) {
        repository.save(person);
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
        var person = repository.findById(id).orElseThrow();
        repository.delete(person);
    }
}

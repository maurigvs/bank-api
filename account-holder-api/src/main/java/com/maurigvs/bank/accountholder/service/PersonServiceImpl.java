package com.maurigvs.bank.accountholder.service;

import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Person person) {
        repository.save(person);
    }

    @Override
    public Person findByTaxId(String taxId) {
        return repository.findByTaxId(taxId).orElseThrow(
            () -> new NoSuchElementException("Person not found by taxId " + taxId)
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

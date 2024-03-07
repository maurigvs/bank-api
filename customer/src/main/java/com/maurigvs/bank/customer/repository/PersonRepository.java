package com.maurigvs.bank.customer.repository;

import com.maurigvs.bank.customer.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByTaxId(String taxId);

}

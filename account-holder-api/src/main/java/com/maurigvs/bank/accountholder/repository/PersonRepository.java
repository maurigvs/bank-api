package com.maurigvs.bank.accountholder.repository;

import com.maurigvs.bank.accountholder.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByTaxId(String taxId);

}

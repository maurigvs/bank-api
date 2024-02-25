package com.maurigvs.bank.account.repository;

import com.maurigvs.bank.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByTaxId(String taxId);

    Optional<Customer> findByTaxId(String taxId);
}

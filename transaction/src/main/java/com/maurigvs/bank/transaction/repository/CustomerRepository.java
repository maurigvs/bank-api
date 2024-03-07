package com.maurigvs.bank.transaction.repository;

import com.maurigvs.bank.transaction.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

package com.maurigvs.bank.customer.repository;

import com.maurigvs.bank.customer.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByTaxId(String taxId);

}

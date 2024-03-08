package com.maurigvs.bank.checkingaccount.repository;

import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
}
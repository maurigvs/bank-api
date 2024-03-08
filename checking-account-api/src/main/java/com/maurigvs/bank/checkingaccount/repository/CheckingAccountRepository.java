package com.maurigvs.bank.checkingaccount.repository;

import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
}

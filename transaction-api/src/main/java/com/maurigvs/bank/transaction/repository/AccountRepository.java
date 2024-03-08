package com.maurigvs.bank.transaction.repository;

import com.maurigvs.bank.transaction.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}

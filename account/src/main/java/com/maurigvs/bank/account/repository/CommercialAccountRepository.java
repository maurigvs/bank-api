package com.maurigvs.bank.account.repository;

import com.maurigvs.bank.account.model.CommercialAccount;
import com.maurigvs.bank.account.model.ConsumerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialAccountRepository extends JpaRepository<CommercialAccount, Long> {
}
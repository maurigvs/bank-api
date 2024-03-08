package com.maurigvs.bank.checkingaccount.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class CheckingAccount extends Account {

    public CheckingAccount(Long id, LocalDate joinedAt, Integer pinCode, AccountHolder accountHolder) {
        super(id, joinedAt, pinCode, accountHolder);
    }

    protected CheckingAccount() {
        super();
    }
}

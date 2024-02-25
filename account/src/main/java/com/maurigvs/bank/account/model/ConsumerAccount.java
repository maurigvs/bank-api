package com.maurigvs.bank.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class ConsumerAccount extends Account {

    public ConsumerAccount(Long id, LocalDate joinedAt, Integer pinCode, Customer customer) {
        super(id, joinedAt, pinCode, customer);
    }

    protected ConsumerAccount() {
        super();
    }
}

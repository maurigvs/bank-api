package com.maurigvs.bank.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class ConsumerAccount extends Account {

    public ConsumerAccount(Long id, String taxId, LocalDate joinedAt) {
        super(id, taxId, joinedAt);
    }

    protected ConsumerAccount() {
        super();
    }
}
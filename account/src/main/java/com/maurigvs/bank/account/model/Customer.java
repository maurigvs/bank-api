package com.maurigvs.bank.account.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    private Long id;

    private String taxId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Account> accountList = new ArrayList<>();

    public Customer(Long id, String taxId) {
        this.id = id;
        this.taxId = taxId;
    }

    protected Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getTaxId() {
        return taxId;
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}

package com.maurigvs.bank.account.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxId;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
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

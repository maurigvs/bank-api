package com.maurigvs.bank.transaction.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Account> accountList = new ArrayList<>();

    public Customer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}

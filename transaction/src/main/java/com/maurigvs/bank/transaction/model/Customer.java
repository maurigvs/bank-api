package com.maurigvs.bank.transaction.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Account> accountList = new ArrayList<>();

    public Customer(Long id) {
        this.id = id;
    }

    protected Customer(){
    }

    public Long getId() {
        return id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}

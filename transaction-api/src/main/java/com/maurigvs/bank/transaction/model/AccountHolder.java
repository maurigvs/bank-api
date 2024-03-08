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
public class AccountHolder implements Serializable {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountHolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CheckingAccount> checkingAccountList = new ArrayList<>();

    public AccountHolder(Long id) {
        this.id = id;
    }

    protected AccountHolder(){
    }

    public Long getId() {
        return id;
    }

    public List<CheckingAccount> getCheckingAccountList() {
        return checkingAccountList;
    }
}

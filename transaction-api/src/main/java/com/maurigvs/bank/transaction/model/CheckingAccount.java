package com.maurigvs.bank.transaction.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CheckingAccount implements Serializable {

    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "checkingAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Transaction> transactionList = new ArrayList<>();

    public CheckingAccount(Long id, AccountHolder accountHolder) {
        this.id = id;
        this.accountHolder = accountHolder;
    }

    protected CheckingAccount() {
    }

    public Long getId() {
        return id;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}

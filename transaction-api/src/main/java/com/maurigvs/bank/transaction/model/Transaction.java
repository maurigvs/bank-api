package com.maurigvs.bank.transaction.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private String description;

    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "checking_account_id")
    private CheckingAccount checkingAccount;

    public Transaction(Long id, LocalDateTime dateTime, String description, Double amount, CheckingAccount checkingAccount) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.amount = amount;
        this.checkingAccount = checkingAccount;
    }

    protected Transaction() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }
}

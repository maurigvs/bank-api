package com.maurigvs.bank.account.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate joinedAt;

    private Integer pinCode;

    private Double balance = 0.0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account(Long id, LocalDate joinedAt, Integer pinCode, Customer customer) {
        this.id = id;
        this.joinedAt = joinedAt;
        this.pinCode = pinCode;
        this.customer = customer;
    }

    protected Account() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public Double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }
}

package com.maurigvs.bank.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxId;

    private LocalDate joinedAt;

    public Customer(Long id, String taxId, LocalDate joinedAt) {
        this.id = id;
        this.taxId = taxId;
        this.joinedAt = joinedAt;
    }

    protected Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getTaxId() {
        return taxId;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }
}

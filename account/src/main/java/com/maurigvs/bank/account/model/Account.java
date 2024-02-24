package com.maurigvs.bank.account.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxId;

    private LocalDate joinedAt;

    public Account(Long id, String taxId, LocalDate joinedAt) {
        this.id = id;
        this.taxId = taxId;
        this.joinedAt = joinedAt;
    }

    protected Account() {
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

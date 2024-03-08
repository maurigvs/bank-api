package com.maurigvs.bank.accountholder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taxId;

    private LocalDate joinedAt;

    public AccountHolder(Long id, String taxId, LocalDate joinedAt) {
        this.id = id;
        this.taxId = taxId;
        this.joinedAt = joinedAt;
    }

    protected AccountHolder() {
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

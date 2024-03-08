package com.maurigvs.bank.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "customer_id")
public class Company extends Customer {

    private String businessName;
    private String legalName;
    private LocalDate openingDate;

    public Company(Long id, String taxId, LocalDate joinedAt, String businessName, String legalName, LocalDate openingDate) {
        super(id, taxId, joinedAt);
        this.businessName = businessName;
        this.legalName = legalName;
        this.openingDate = openingDate;
    }

    protected Company() {
        super();
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getLegalName() {
        return legalName;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }
}

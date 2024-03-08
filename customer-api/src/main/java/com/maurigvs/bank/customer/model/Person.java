package com.maurigvs.bank.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "customer_id")
public class Person extends Customer {

    private String name;
    private String surname;
    private LocalDate birthDate;

    public Person(Long id, String taxId, LocalDate joinedAt, String name, String surname, LocalDate birthDate) {
        super(id, taxId, joinedAt);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    protected Person() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}

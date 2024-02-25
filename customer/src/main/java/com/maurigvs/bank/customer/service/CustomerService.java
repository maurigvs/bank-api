package com.maurigvs.bank.customer.service;

import com.maurigvs.bank.customer.dto.PersonResponse;
import com.maurigvs.bank.customer.model.Customer;

import java.util.List;

public interface CustomerService<T extends Customer> {

    void create(T customer);

    T findByTaxId(String taxId);

    List<T> findAll();

    void updateById(Long id, T update);

    void deleteById(Long id);
}

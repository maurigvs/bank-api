package com.maurigvs.bank.accountholder.service;

import com.maurigvs.bank.accountholder.model.AccountHolder;

import java.util.List;

public interface AccountHolderService<T extends AccountHolder> {

    void create(T t);

    T findByTaxId(String taxId);

    List<T> findAll();

    void deleteById(Long id);
}

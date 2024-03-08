package com.maurigvs.bank.checkingaccount.service;

import com.maurigvs.bank.checkingaccount.model.Account;

import java.util.List;

public interface AccountService<T extends Account> {

    /**
     * Create new account
     * @param t account instance
     */
    void openAccount(T t);

    /**
     * List all accounts
     * @return accounts list
     */
    List<T> findAllAccounts();

    /**
     * Get a Account by Id
     * @param id Account Id
     * @return Account instance
     */
    T findById(Long id);

    /**
     * Delete existing account
     * @param id id of the account
     */
    void closeAccount(Long id);
}

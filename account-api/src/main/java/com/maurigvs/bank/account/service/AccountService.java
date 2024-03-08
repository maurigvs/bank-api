package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.model.Account;

import java.util.List;

public interface AccountService<T extends Account> {

    /**
     * Create new account
     * @param account account instance
     */
    void openAccount(T account);

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
     * Update Account balance by Id
     * @param id
     * @param amount
     * @return
     */
    double updateBalance(Long id, Double amount);

    /**
     * Delete existing account
     * @param id id of the account
     */
    void closeAccount(Long id);
}

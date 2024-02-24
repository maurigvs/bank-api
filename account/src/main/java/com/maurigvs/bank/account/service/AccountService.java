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
     * Update account
     * @param id id of the account
     * @param account account instance
     */
    void updateAccount(Long id, T account);

    /**
     * Delete existing account
     * @param id id of the account
     */
    void closeAccount(Long id);

}

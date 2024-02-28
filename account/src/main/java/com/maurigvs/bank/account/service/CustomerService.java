package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.model.Customer;

public interface CustomerService {

    /**
     * Find a Customer by taxId
     * @param taxId tax id of customer
     * @return customer instance
     */
    Customer findByTaxId(String taxId);

}

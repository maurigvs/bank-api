package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.CheckingAccount;

public interface CheckingAccountService {

    CheckingAccount findById(Long id);

}

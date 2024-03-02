package com.maurigvs.bank.account.mapper;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.model.CommercialAccount;
import com.maurigvs.bank.account.model.Customer;

import java.time.LocalDate;
import java.util.function.Function;

public class CommercialAccountMapper implements Function<AccountRequest, CommercialAccount> {

    private final Customer customer;

    public CommercialAccountMapper(Customer customer) {
        this.customer = customer;
    }

    @Override
    public CommercialAccount apply(AccountRequest request) {
        var joinedAt = LocalDate.now();

        return new CommercialAccount(null, joinedAt, request.pinCode(), customer);
    }
}

package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.mapper.AccountResponseMapper;
import com.maurigvs.bank.account.mapper.CommercialAccountMapper;
import com.maurigvs.bank.account.service.CommercialAccountService;
import com.maurigvs.bank.account.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commercial")
public class CommercialAccountController {

    private final CommercialAccountService accountService;
    private final CustomerService customerService;

    public CommercialAccountController(CommercialAccountService accountService,
                                       CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCommercialAccount(@RequestBody @Valid AccountRequest request){
        var customer = customerService.findByTaxId(request.taxId());
        var account = new CommercialAccountMapper(customer).apply(request);
        accountService.openAccount(account);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> getCommercialAccountList(){
        return accountService.findAllAccounts().stream()
                .map(new AccountResponseMapper<>())
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCommercialAccountById(@PathVariable Long id){
        accountService.closeAccount(id);
    }
}
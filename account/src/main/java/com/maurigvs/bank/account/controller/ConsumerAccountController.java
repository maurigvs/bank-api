package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.mapper.AccountResponseMapper;
import com.maurigvs.bank.account.mapper.ConsumerAccountMapper;
import com.maurigvs.bank.account.service.ConsumerAccountService;
import com.maurigvs.bank.account.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerAccountController {

    private final ConsumerAccountService accountService;
    private final CustomerService customerService;

    public ConsumerAccountController(ConsumerAccountService accountService,
                                     CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postConsumerAccount(@RequestBody @Valid AccountRequest request){
        var customer = customerService.findByTaxId(request.taxId());
        var account = new ConsumerAccountMapper(customer).apply(request);
        accountService.openAccount(account);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> getConsumerAccountList(){
        return accountService.findAllAccounts().stream()
                .map(new AccountResponseMapper<>())
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConsumerAccountById(@PathVariable Long id){
        accountService.closeAccount(id);
    }
}

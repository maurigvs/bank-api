package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.grpc.CustomerGrpcClient;
import com.maurigvs.bank.account.mapper.AccountResponseMapper;
import com.maurigvs.bank.account.mapper.ConsumerAccountMapper;
import com.maurigvs.bank.account.service.ConsumerAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerAccountController {

    private final ConsumerAccountService accountService;
    private final CustomerGrpcClient customerGrpcClient;

    public ConsumerAccountController(ConsumerAccountService accountService,
                                     CustomerGrpcClient customerGrpcClient) {
        this.accountService = accountService;
        this.customerGrpcClient = customerGrpcClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postConsumerAccount(@RequestBody @Valid AccountRequest request){
        var customer = customerGrpcClient.findByTaxId(request.taxId());
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

package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.grpc.CustomerGrpcClient;
import com.maurigvs.bank.account.mapper.AccountResponseMapper;
import com.maurigvs.bank.account.mapper.CommercialAccountMapper;
import com.maurigvs.bank.account.service.CommercialAccountService;
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
@RequestMapping("/commercial")
public class CommercialAccountController {

    private final CommercialAccountService accountService;
    private final CustomerGrpcClient customerGrpcClient;

    public CommercialAccountController(CommercialAccountService accountService,
                                       CustomerGrpcClient customerGrpcClient) {
        this.accountService = accountService;
        this.customerGrpcClient = customerGrpcClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCommercialAccount(@RequestBody @Valid AccountRequest request){
        var customer = customerGrpcClient.findByTaxId(request.taxId());
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

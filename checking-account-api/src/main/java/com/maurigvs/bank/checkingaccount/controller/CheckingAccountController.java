package com.maurigvs.bank.checkingaccount.controller;

import com.maurigvs.bank.checkingaccount.dto.AccountRequest;
import com.maurigvs.bank.checkingaccount.dto.AccountResponse;
import com.maurigvs.bank.checkingaccount.grpc.client.AccountHolderGrpcClient;
import com.maurigvs.bank.checkingaccount.mapper.AccountResponseMapper;
import com.maurigvs.bank.checkingaccount.mapper.CheckingAccountMapper;
import com.maurigvs.bank.checkingaccount.service.CheckingAccountService;
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
@RequestMapping("/checking-account")
public class CheckingAccountController {

    private final CheckingAccountService service;
    private final AccountHolderGrpcClient grpcClient;

    public CheckingAccountController(CheckingAccountService service,
                                     AccountHolderGrpcClient grpcClient) {
        this.service = service;
        this.grpcClient = grpcClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCheckingAccount(@RequestBody @Valid AccountRequest request){
        var accountHolder = grpcClient.findByTaxId(request.taxId());
        var checkingAccount = new CheckingAccountMapper(accountHolder).apply(request);
        service.openAccount(checkingAccount);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> getCheckingAccountList(){
        return service.findAllAccounts().stream()
                .map(new AccountResponseMapper<>())
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCheckingAccountById(@PathVariable Long id){
        service.closeAccount(id);
    }
}

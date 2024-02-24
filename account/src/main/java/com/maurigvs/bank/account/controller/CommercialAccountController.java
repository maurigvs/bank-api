package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.mapper.AccountResponseMapper;
import com.maurigvs.bank.account.mapper.CommercialAccountMapper;
import com.maurigvs.bank.account.service.CommercialAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commercial")
public class CommercialAccountController {

    private final CommercialAccountService service;

    public CommercialAccountController(CommercialAccountService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCommercialAccount(@RequestBody @Valid AccountRequest request){
        var account = new CommercialAccountMapper().apply(request);
        service.openAccount(account);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> getCommercialAccountList(){
        return service.findAllAccounts().stream()
                .map(new AccountResponseMapper<>())
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putCommercialAccountUpdate(@PathVariable Long id,
                                           @RequestBody @Valid AccountRequest request){
        var account = new CommercialAccountMapper().apply(request);
        service.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCommercialAccountById(@PathVariable Long id){
        service.closeAccount(id);
    }
}

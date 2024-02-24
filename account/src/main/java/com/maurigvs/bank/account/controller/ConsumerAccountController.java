package com.maurigvs.bank.account.controller;

import com.maurigvs.bank.account.dto.AccountRequest;
import com.maurigvs.bank.account.dto.AccountResponse;
import com.maurigvs.bank.account.mapper.AccountResponseMapper;
import com.maurigvs.bank.account.mapper.ConsumerAccountMapper;
import com.maurigvs.bank.account.service.ConsumerAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerAccountController {

    private final ConsumerAccountService service;

    public ConsumerAccountController(ConsumerAccountService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postAccount(@RequestBody AccountRequest request){
        var account = new ConsumerAccountMapper().apply(request);
        service.openAccount(account);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> getList(){
        return service.findAllAccounts().stream()
                .map(new AccountResponseMapper<>())
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putAccount(@PathVariable Long id, @RequestBody AccountRequest request){
        var account = new ConsumerAccountMapper().apply(request);
        service.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id){
        service.closeAccount(id);
    }
}

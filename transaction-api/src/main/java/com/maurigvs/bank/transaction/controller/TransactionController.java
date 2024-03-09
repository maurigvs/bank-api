package com.maurigvs.bank.transaction.controller;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.dto.TransactionResponse;
import com.maurigvs.bank.transaction.mapper.TransactionMapper;
import com.maurigvs.bank.transaction.mapper.TransactionResponseMapper;
import com.maurigvs.bank.transaction.service.CheckingAccountService;
import com.maurigvs.bank.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class TransactionController {

    private final TransactionService transactionService;
    private final CheckingAccountService accountService;

    public TransactionController(TransactionService transactionService,
                                 CheckingAccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody @Valid TransactionRequest request){
        var checkingAccount = accountService.findById(request.checkingAccountId());
        var transaction = new TransactionMapper(checkingAccount).apply(request);
        transactionService.create(transaction);
    }

    @GetMapping("/{checkingAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getById(@PathVariable Long checkingAccountId){
        return accountService.findById(checkingAccountId)
                .getTransactionList().stream()
                .map(new TransactionResponseMapper())
                .toList();
    }
}

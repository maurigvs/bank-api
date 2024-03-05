package com.maurigvs.bank.transaction.controller;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.dto.TransactionResponse;
import com.maurigvs.bank.transaction.grpc.AccountApiService;
import com.maurigvs.bank.transaction.mapper.TransactionMapper;
import com.maurigvs.bank.transaction.mapper.TransactionResponseMapper;
import com.maurigvs.bank.transaction.service.TransactionService;
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
    private final AccountApiService accountApiService;

    public TransactionController(TransactionService transactionService,
                                 AccountApiService accountApiService) {
        this.transactionService = transactionService;
        this.accountApiService = accountApiService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction(@RequestBody TransactionRequest request){
        var account = accountApiService.findById(request.accountId());
        var transaction = new TransactionMapper(account).apply(request);
        transactionService.create(transaction);
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getTransactionsByAccountId(@PathVariable Long accountId){
        return transactionService.findByAccountId(accountId).stream().map(new TransactionResponseMapper()).toList();
    }
}

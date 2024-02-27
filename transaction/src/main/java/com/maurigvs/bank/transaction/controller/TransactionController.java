package com.maurigvs.bank.transaction.controller;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.mapper.TransactionMapper;
import com.maurigvs.bank.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction(@RequestBody TransactionRequest request){
        var transaction = new TransactionMapper().apply(request);
        service.create(transaction);
    }
}

package com.maurigvs.bank.transaction.controller;

import com.maurigvs.bank.transaction.dto.TransactionRequest;
import com.maurigvs.bank.transaction.dto.TransactionResponse;
import com.maurigvs.bank.transaction.grpc.client.CheckingAccountGrpcClient;
import com.maurigvs.bank.transaction.mapper.TransactionMapper;
import com.maurigvs.bank.transaction.mapper.TransactionResponseMapper;
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

    private final TransactionService service;
    private final CheckingAccountGrpcClient grpcClient;

    public TransactionController(TransactionService service, CheckingAccountGrpcClient grpcClient) {
        this.service = service;
        this.grpcClient = grpcClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody @Valid TransactionRequest request){
        var checkingAccount = grpcClient.findById(request.checkingAccountId());
        var transaction = new TransactionMapper(checkingAccount).apply(request);
        service.create(transaction);
    }

    @GetMapping("/{checkingAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getById(@PathVariable Long checkingAccountId){
        return service.findByCheckingAccountId(checkingAccountId).stream()
                .map(new TransactionResponseMapper())
                .toList();
    }
}

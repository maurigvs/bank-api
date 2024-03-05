package com.maurigvs.bank.account.config;

import com.maurigvs.bank.account.grpc.AccountGrpcService;
import com.maurigvs.bank.account.service.ConsumerAccountService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Bean
    public Server serverBuilder(ConsumerAccountService consumerAccountService){
        return ServerBuilder.forPort(8181)
                .directExecutor()
                .addService(new AccountGrpcService(consumerAccountService))
                .build();
    }
}

package com.maurigvs.bank.account.config;

import com.maurigvs.bank.account.grpc.AccountGrpcServer;
import com.maurigvs.bank.account.service.ConsumerAccountService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Value("${bank.grpc-server.port}")
    private Integer port;

    @Bean
    public Server serverBuilder(ConsumerAccountService consumerAccountService){
        return ServerBuilder.forPort(port)
                .directExecutor()
                .addService(new AccountGrpcServer(consumerAccountService))
                .build();
    }
}

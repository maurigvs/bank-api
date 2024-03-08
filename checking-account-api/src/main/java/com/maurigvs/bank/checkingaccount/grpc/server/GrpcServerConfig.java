package com.maurigvs.bank.checkingaccount.grpc.server;

import com.maurigvs.bank.checkingaccount.service.CheckingAccountService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Value("${bank.grpc-server.port}")
    private Integer port;

    @Bean
    public Server serverBuilder(CheckingAccountService checkingAccountService){
        return ServerBuilder.forPort(port)
                .directExecutor()
                .addService(new CheckingAccountGrpcServer(checkingAccountService))
                .build();
    }
}

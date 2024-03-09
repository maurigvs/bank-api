package com.maurigvs.bank.accountholder.grpc.server.config;

import com.maurigvs.bank.accountholder.grpc.server.AccountHolderGrpcServer;
import com.maurigvs.bank.accountholder.grpc.server.calls.FindByTaxIdGrpcCall;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Value("${bank.grpc-server.port}")
    private Integer port;

    private final FindByTaxIdGrpcCall findByTaxIdGrpcCall;

    public GrpcConfig(FindByTaxIdGrpcCall findByTaxIdGrpcCall) {
        this.findByTaxIdGrpcCall = findByTaxIdGrpcCall;
    }

    @Bean
    public Server serverBuilder(){
        return ServerBuilder.forPort(port)
                .directExecutor()
                .addService(new AccountHolderGrpcServer(findByTaxIdGrpcCall))
                .build();
    }
}

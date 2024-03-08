package com.maurigvs.bank.transaction.config;

import com.maurigvs.bank.grpc.AccountServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClient {

    @Value("${bank.grpc-client.account.host}")
    private String host;

    @Value("${bank.grpc-client.account.port}")
    private Integer port;

    @Bean
    public ManagedChannel managedChannel(){
        return ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public AccountServiceGrpc.AccountServiceBlockingStub accountStub(ManagedChannel managedChannel){
        return AccountServiceGrpc.newBlockingStub(managedChannel);
    }
}

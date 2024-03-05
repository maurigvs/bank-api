package com.maurigvs.bank.transaction.config;

import com.maurigvs.bank.grpc.AccountServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClient {

    @Bean
    public ManagedChannel managedChannel(){
        return ManagedChannelBuilder
                .forAddress("localhost", 8181)
                .usePlaintext()
                .build();
    }

    @Bean
    public AccountServiceGrpc.AccountServiceBlockingStub accountStub(ManagedChannel managedChannel){
        return AccountServiceGrpc.newBlockingStub(managedChannel);
    }
}

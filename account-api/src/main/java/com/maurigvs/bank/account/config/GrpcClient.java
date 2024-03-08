package com.maurigvs.bank.account.config;

import com.maurigvs.bank.grpc.CustomerServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClient {

    @Bean
    public ManagedChannel managedChannel(){
        return ManagedChannelBuilder
                .forAddress("localhost", 8182)
                .usePlaintext()
                .build();
    }

    @Bean
    public CustomerServiceGrpc.CustomerServiceBlockingStub customerStub(ManagedChannel channel){
        return CustomerServiceGrpc.newBlockingStub(channel);
    }
}

package com.maurigvs.bank.account.config;

import com.maurigvs.bank.grpc.CustomerServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClient {

    @Value("${bank.grpc-client.customer.host}")
    private String host;

    @Value("${bank.grpc-client.customer.port}")
    private Integer port;

    @Bean
    public ManagedChannel managedChannel(){
        return ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public CustomerServiceGrpc.CustomerServiceBlockingStub customerStub(ManagedChannel channel){
        return CustomerServiceGrpc.newBlockingStub(channel);
    }
}

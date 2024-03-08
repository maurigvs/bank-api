package com.maurigvs.bank.transaction.grpc.client;

import com.maurigvs.bank.grpc.CheckingAccountServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClient {

    @Value("${bank.grpc-client.checking-account.host}")
    private String host;

    @Value("${bank.grpc-client.checking-account.port}")
    private Integer port;

    @Bean
    public ManagedChannel managedChannel(){
        return ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public CheckingAccountServiceGrpc.CheckingAccountServiceBlockingStub checkingAccountStub(ManagedChannel managedChannel){
        return CheckingAccountServiceGrpc.newBlockingStub(managedChannel);
    }
}

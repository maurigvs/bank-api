package com.maurigvs.bank.accountholder.grpc.server;

import com.maurigvs.bank.accountholder.service.PersonService;
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
    public Server serverBuilder(PersonService personService){
        return ServerBuilder.forPort(port)
                .directExecutor()
                .addService(new AccountHolderGrpcServer(personService))
                .build();
    }
}

package com.maurigvs.bank.customer.config;

import com.maurigvs.bank.customer.grpc.CustomerGrpcService;
import com.maurigvs.bank.customer.service.PersonService;
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
                .addService(new CustomerGrpcService(personService))
                .build();
    }
}

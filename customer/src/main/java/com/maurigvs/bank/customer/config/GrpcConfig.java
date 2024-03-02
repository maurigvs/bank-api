package com.maurigvs.bank.customer.config;

import com.maurigvs.bank.customer.grpc.CustomerGrpcService;
import com.maurigvs.bank.customer.service.PersonService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Bean
    public Server serverBuilder(PersonService personService){
        return ServerBuilder.forPort(8182)
                .directExecutor()
                .addService(new CustomerGrpcService(personService))
                .build();
    }
}

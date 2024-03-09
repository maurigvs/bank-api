package com.maurigvs.bank.checkingaccount.grpc.server;

import com.maurigvs.bank.checkingaccount.service.CheckingAccountService;
import io.grpc.Server;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {GrpcServerConfig.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GrpcServerConfigTest {

    @Autowired
    GrpcServerConfig grpcServerConfig;

    @MockBean
    CheckingAccountService service;

    @Test
    void should_return_Server_instance() {
        var result = grpcServerConfig.serverBuilder(service);

        assertNotNull(result);
        assertInstanceOf(Server.class, result);
    }
}
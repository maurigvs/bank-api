package com.maurigvs.bank.accountholder.grpc.server;

import com.maurigvs.bank.accountholder.service.PersonService;
import io.grpc.Server;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {GrpcConfig.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GrpcConfigTest {

    @Autowired
    GrpcConfig grpcConfig;

    @MockBean
    PersonService personService;

    @Test
    void should_return_Server_instance() {
        var result = grpcConfig.serverBuilder(personService);

        assertNotNull(result);
        assertInstanceOf(Server.class, result);
    }
}
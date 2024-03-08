package com.maurigvs.bank.transaction.config;

import com.maurigvs.bank.grpc.CheckingAccountServiceGrpc;
import com.maurigvs.bank.transaction.grpc.client.GrpcClient;
import io.grpc.ManagedChannel;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest(classes = {GrpcClient.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GrpcClientTest {

    @Autowired
    GrpcClient grpcClient;

    @Test
    void should_provide_an_ManagedChannel() {
        assertInstanceOf(ManagedChannel.class, grpcClient.managedChannel());
    }

    @Test
    void should_provide_an_CheckingAccountServiceBlockingStub_instance() {
        var managedChannel = grpcClient.managedChannel();
        assertInstanceOf(
            CheckingAccountServiceGrpc.CheckingAccountServiceBlockingStub.class,
            grpcClient.checkingAccountStub(managedChannel)
        );
    }
}
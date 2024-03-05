package com.maurigvs.bank.transaction.config;

import com.maurigvs.bank.grpc.AccountServiceGrpc;
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
    void should_provide_an_AccountServiceBlockingStub_instance() {
        var managedChannel = grpcClient.managedChannel();
        assertInstanceOf(AccountServiceGrpc.AccountServiceBlockingStub.class, grpcClient.accountStub(managedChannel));
    }
}
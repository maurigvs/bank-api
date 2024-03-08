package com.maurigvs.bank.checkingaccount.grpc.client;

import com.maurigvs.bank.grpc.AccountHolderServiceGrpc;
import io.grpc.ManagedChannel;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest(classes = {GrpcClientConfig.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GrpcClientConfigTest {

    @Autowired
    GrpcClientConfig grpcClientConfig;

    @Test
    void should_provide_an_ManagedChannel() {
        assertInstanceOf(ManagedChannel.class, grpcClientConfig.managedChannel());
    }

    @Test
    void should_provide_an_AccountHolderServiceBlockingStub_instance() {
        var managedChannel = grpcClientConfig.managedChannel();
        assertInstanceOf(
            AccountHolderServiceGrpc.AccountHolderServiceBlockingStub.class,
            grpcClientConfig.accountHolderServiceBlockingStub(managedChannel));
    }
}
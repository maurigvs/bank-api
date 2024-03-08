package com.maurigvs.bank.checkingaccount.grpc.client;

import com.maurigvs.bank.checkingaccount.exception.AccountHolderApiException;
import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.AccountHolderServiceGrpc;
import com.maurigvs.bank.grpc.FindAccountHolderReply;
import com.maurigvs.bank.grpc.FindAccountHolderRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {AccountHolderGrpcClient.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountHolderGrpcClientTest {

    @Autowired
    AccountHolderGrpcClient grpcClient;

    @MockBean
    AccountHolderServiceGrpc.AccountHolderServiceBlockingStub blockingStub;

    @Captor
    ArgumentCaptor<FindAccountHolderRequest> argumentCaptor;

    @Nested
    class findByTaxId {

        @Test
        void should_return_AccountHolder() {
            var expectedRequest = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
            var accountHolderData = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
            var expectedReply = FindAccountHolderReply.newBuilder().setAccountHolderData(accountHolderData).build();
            given(blockingStub.findByTaxId(any())).willReturn(expectedReply);

            var result = grpcClient.findByTaxId("12345");

            then(blockingStub).should().findByTaxId(argumentCaptor.capture());
            assertEquals(expectedRequest, argumentCaptor.getValue());

            assertEquals(accountHolderData.getId(), result.getId());
            assertEquals(accountHolderData.getTaxId(), result.getTaxId());
        }

        @Test
        void should_throw_EntityNotFoundException_when_Status_Not_Found_is_received() {
            given(blockingStub.findByTaxId(any())).willThrow(
                new StatusRuntimeException(
                    Status.NOT_FOUND.withDescription("Person not found by taxId 12345")));

            var result = assertThrows(
                    EntityNotFoundException.class,
                    () -> grpcClient.findByTaxId("12345"));

            assertEquals("Person not found by taxId 12345", result.getMessage());
        }

        @Test
        void should_throw_AccountHolderApiException_when_StatusRuntimeException_is_received() {
            given(blockingStub.findByTaxId(any())).willThrow(
                new StatusRuntimeException(
                        Status.UNAVAILABLE.withCause(new Throwable("io exception"))));

            var result = assertThrows(
                    AccountHolderApiException.class,
                    () -> grpcClient.findByTaxId("12345"));

            assertEquals("UNAVAILABLE > io exception", result.getLocalizedMessage());
        }
    }
}
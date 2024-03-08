package com.maurigvs.bank.checkingaccount.grpc.client;

import com.maurigvs.bank.checkingaccount.exception.AccountHolderApiException;
import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.grpc.CustomerData;
import com.maurigvs.bank.grpc.CustomerServiceGrpc;
import com.maurigvs.bank.grpc.FindCustomerReply;
import com.maurigvs.bank.grpc.FindCustomerRequest;
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
    CustomerServiceGrpc.CustomerServiceBlockingStub blockingStub;

    @Captor
    ArgumentCaptor<FindCustomerRequest> argumentCaptor;

    @Nested
    class findByTaxId {

        @Test
        void should_return_Customer() {
            var expectedRequest = FindCustomerRequest.newBuilder().setTaxId("12345").build();
            var customer = CustomerData.newBuilder().setId(1L).setTaxId("12345").build();
            var expectedReply = FindCustomerReply.newBuilder().setCustomerData(customer).build();
            given(blockingStub.findByTaxId(any())).willReturn(expectedReply);

            var result = grpcClient.findByTaxId("12345");

            then(blockingStub).should().findByTaxId(argumentCaptor.capture());
            assertEquals(expectedRequest, argumentCaptor.getValue());

            assertEquals(customer.getId(), result.getId());
            assertEquals(customer.getTaxId(), result.getTaxId());
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
        void should_throw_CustomerApiException_when_StatusRuntimeException_is_received() {
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
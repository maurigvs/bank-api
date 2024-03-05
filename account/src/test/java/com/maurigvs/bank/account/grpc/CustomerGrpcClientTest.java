package com.maurigvs.bank.account.grpc;

import com.maurigvs.bank.account.exception.CustomerApiException;
import com.maurigvs.bank.account.exception.EntityNotFoundException;
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

@SpringBootTest(classes = {CustomerGrpcClient.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerGrpcClientTest {

    @Autowired
    CustomerGrpcClient customerGrpcClient;

    @MockBean
    CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub;

    @Captor
    ArgumentCaptor<FindCustomerRequest> requestArgumentCaptor;

    @Nested
    class findByTaxId {

        @Test
        void should_return_Customer() {
            var expectedRequest = FindCustomerRequest.newBuilder().setTaxId("12345").build();
            var customer = CustomerData.newBuilder().setId(1L).setTaxId("12345").build();
            var expectedReply = FindCustomerReply.newBuilder().setCustomerData(customer).build();
            given(customerServiceBlockingStub.findByTaxId(any())).willReturn(expectedReply);

            var result = customerGrpcClient.findByTaxId("12345");

            then(customerServiceBlockingStub).should().findByTaxId(requestArgumentCaptor.capture());
            assertEquals(expectedRequest, requestArgumentCaptor.getValue());

            assertEquals(customer.getId(), result.getId());
            assertEquals(customer.getTaxId(), result.getTaxId());
        }

        @Test
        void should_throw_EntityNotFoundException_when_Status_Not_Found_is_received() {
            given(customerServiceBlockingStub.findByTaxId(any())).willThrow(
                new StatusRuntimeException(
                    Status.NOT_FOUND.withDescription("Person not found by taxId 12345")));

            var result = assertThrows(
                    EntityNotFoundException.class,
                    () -> customerGrpcClient.findByTaxId("12345"));

            assertEquals("Person not found by taxId 12345", result.getMessage());
        }

        @Test
        void should_throw_CustomerApiException_when_StatusRuntimeException_is_received() {
            given(customerServiceBlockingStub.findByTaxId(any())).willThrow(
                new StatusRuntimeException(
                        Status.UNAVAILABLE.withCause(new Throwable("io exception"))));

            var result = assertThrows(
                    CustomerApiException.class,
                    () -> customerGrpcClient.findByTaxId("12345"));

            assertEquals("UNAVAILABLE > io exception", result.getLocalizedMessage());
        }
    }
}
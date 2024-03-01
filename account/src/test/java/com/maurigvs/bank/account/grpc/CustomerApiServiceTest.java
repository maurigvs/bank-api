package com.maurigvs.bank.account.grpc;

import com.maurigvs.bank.CustomerGrpcGrpc;
import com.maurigvs.bank.CustomerReply;
import com.maurigvs.bank.account.exception.CustomerApiException;
import com.maurigvs.bank.account.exception.EntityNotFoundException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {CustomerApiService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerApiServiceTest {

    @Autowired
    CustomerApiService customerApiService;

    @MockBean
    CustomerGrpcGrpc.CustomerGrpcBlockingStub customerGrpcBlockingStub;

    @Nested
    class findByTaxId {

        @Test
        void should_return_Customer() {
            var reply = CustomerReply.newBuilder().setId(1L).setTaxId("12345").build();
            given(customerGrpcBlockingStub.findByTaxId(any())).willReturn(reply);

            var result = customerApiService.findByTaxId("12345");

            assertEquals(reply.getId(), result.getId());
            assertEquals(reply.getTaxId(), result.getTaxId());
        }

        @Test
        void should_throw_EntityNotFoundException_when_Status_Not_Found_is_received() {
            given(customerGrpcBlockingStub.findByTaxId(any())).willThrow(
                new StatusRuntimeException(
                    Status.NOT_FOUND.withDescription("Person not found by taxId 12345")));

            var result = assertThrows(
                    EntityNotFoundException.class,
                    () -> customerApiService.findByTaxId("12345"));

            assertEquals("Person not found by taxId 12345", result.getMessage());
        }

        @Test
        void should_throw_CustomerApiException_when_StatusRuntimeException_is_received() {
            given(customerGrpcBlockingStub.findByTaxId(any())).willThrow(
                new StatusRuntimeException(
                        Status.UNAVAILABLE.withCause(new Throwable("io exception"))));

            var result = assertThrows(
                    CustomerApiException.class,
                    () -> customerApiService.findByTaxId("12345"));

            assertEquals("UNAVAILABLE > io exception", result.getLocalizedMessage());
        }
    }
}
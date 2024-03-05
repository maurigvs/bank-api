package com.maurigvs.bank.transaction.grpc;

import com.maurigvs.bank.grpc.AccountData;
import com.maurigvs.bank.grpc.AccountServiceGrpc;
import com.maurigvs.bank.grpc.CustomerData;
import com.maurigvs.bank.grpc.FindAccountReply;
import com.maurigvs.bank.grpc.FindAccountRequest;
import com.maurigvs.bank.transaction.exception.AccountApiException;
import com.maurigvs.bank.transaction.exception.EntityNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {AccountApiService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountApiServiceTest {

    @Autowired
    AccountApiService accountApiService;

    @MockBean
    AccountServiceGrpc.AccountServiceBlockingStub accountGrpcStub;

    @Captor
    ArgumentCaptor<FindAccountRequest> requestCaptor;

    @Nested
    class findById {

        @Test
        void should_return_Account() {
            var id = 1L;
            var expectedRequest = FindAccountRequest.newBuilder().setId(1L).build();
            var customerData = CustomerData.newBuilder().setId(1L).setTaxId("12345").build();
            var accountData = AccountData.newBuilder().setId(1L).setBalance(150.00).setCustomerData(customerData).build();
            var expectedReply = FindAccountReply.newBuilder().setAccountData(accountData).build();
            given(accountGrpcStub.findById(any())).willReturn(expectedReply);

            var result = accountApiService.findById(id);

            then(accountGrpcStub).should().findById(requestCaptor.capture());
            assertEquals(expectedRequest, requestCaptor.getValue());

            assertEquals(1L, result.getId());
            assertEquals(1L, result.getCustomer().getId());
            assertTrue(result.getCustomer().getAccountList().contains(result));
            assertTrue(result.getTransactionList().isEmpty());
        }

        @Test
        void should_throw_EntityNotFoundException_when_Status_Not_Found_is_received() {
            given(accountGrpcStub.findById(any())).willThrow(
                    new StatusRuntimeException(
                            Status.NOT_FOUND.withDescription("Person not found by taxId 12345")));

            var result = assertThrows(
                    EntityNotFoundException.class,
                    () -> accountApiService.findById(1L));

            assertEquals("Person not found by taxId 12345", result.getMessage());
        }

        @Test
        void should_throw_CustomerApiException_when_StatusRuntimeException_is_received() {
            given(accountGrpcStub.findById(any())).willThrow(
                    new StatusRuntimeException(
                            Status.UNAVAILABLE.withCause(new Throwable("io exception"))));

            var result = assertThrows(
                    AccountApiException.class,
                    () -> accountApiService.findById(1L));

            assertEquals("UNAVAILABLE > io exception", result.getLocalizedMessage());
        }
    }
}

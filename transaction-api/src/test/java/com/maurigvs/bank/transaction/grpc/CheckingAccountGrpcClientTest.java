package com.maurigvs.bank.transaction.grpc;

import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.CheckingAccountData;
import com.maurigvs.bank.grpc.CheckingAccountServiceGrpc;
import com.maurigvs.bank.grpc.FindCheckingAccountReply;
import com.maurigvs.bank.grpc.FindCheckingAccountRequest;
import com.maurigvs.bank.transaction.exception.CheckingAccountApiException;
import com.maurigvs.bank.transaction.grpc.client.CheckingAccountGrpcClient;
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

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {CheckingAccountGrpcClient.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CheckingAccountGrpcClientTest {

    @Autowired
    CheckingAccountGrpcClient grpcClient;

    @MockBean
    CheckingAccountServiceGrpc.CheckingAccountServiceBlockingStub blockingStub;

    @Captor
    ArgumentCaptor<FindCheckingAccountRequest> argumentCaptor;

    @Nested
    class findById {

        @Test
        void should_return_CheckingAccount() {
            var id = 1L;
            var expectedRequest = FindCheckingAccountRequest.newBuilder().setId(1L).build();
            var accountHolderData = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
            var accountData = CheckingAccountData.newBuilder().setId(1L).setBalance(150.00).setAccountHolderData(accountHolderData).build();
            var expectedReply = FindCheckingAccountReply.newBuilder().setCheckingAccountData(accountData).build();
            given(blockingStub.findById(any())).willReturn(expectedReply);

            var result = grpcClient.findById(id);

            then(blockingStub).should().findById(argumentCaptor.capture());
            assertEquals(expectedRequest, argumentCaptor.getValue());

            assertEquals(1L, result.getId());
            assertEquals(1L, result.getAccountHolder().getId());
            assertTrue(result.getAccountHolder().getCheckingAccountList().contains(result));
            assertTrue(result.getTransactionList().isEmpty());
        }

        @Test
        void should_throw_EntityNotFoundException_when_Status_Not_Found_is_received() {
            given(blockingStub.findById(any())).willThrow(
                    new StatusRuntimeException(
                            Status.NOT_FOUND.withDescription("Person not found by taxId 12345")));

            var result = assertThrows(NoSuchElementException.class, () -> grpcClient.findById(1L));

            assertEquals("Person not found by taxId 12345", result.getMessage());
        }

        @Test
        void should_throw_AccountHolderApiException_when_StatusRuntimeException_is_received() {
            given(blockingStub.findById(any())).willThrow(
                    new StatusRuntimeException(
                            Status.UNAVAILABLE.withCause(new Throwable("io exception"))));

            var result = assertThrows(CheckingAccountApiException.class, () -> grpcClient.findById(1L));

            assertEquals("UNAVAILABLE > io exception", result.getLocalizedMessage());
        }
    }
}

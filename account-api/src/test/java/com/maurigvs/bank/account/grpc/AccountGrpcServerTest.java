package com.maurigvs.bank.account.grpc;

import com.maurigvs.bank.account.exception.EntityNotFoundException;
import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.account.service.ConsumerAccountService;
import com.maurigvs.bank.grpc.AccountData;
import com.maurigvs.bank.grpc.CustomerData;
import com.maurigvs.bank.grpc.FindAccountReply;
import com.maurigvs.bank.grpc.FindAccountRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {AccountGrpcServer.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountGrpcServerTest {

    @Autowired
    AccountGrpcServer accountGrpcServer;

    @MockBean
    ConsumerAccountService consumerAccountService;

    @Nested
    class findById {

        @Test
        void should_return_Account() {
            var request = FindAccountRequest.newBuilder().setId(1L).build();
            var completed = new AtomicBoolean(false);

            var customerData = CustomerData.newBuilder().setId(1L).setTaxId("12345").build();
            var accountData = AccountData.newBuilder().setId(1L).setBalance(0.0).setCustomerData(customerData).build();
            var expectedReply = FindAccountReply.newBuilder().setAccountData(accountData).build();

            var consumerAccount = new ConsumerAccount(1L,
                    LocalDate.of(2024,1,1), 123456,
                    new Customer(1L, "12345"));
            given(consumerAccountService.findById(anyLong())).willReturn(consumerAccount);

            accountGrpcServer.findById(request, new StreamObserver<>() {
                @Override
                public void onNext(FindAccountReply reply) {
                    assertEquals(expectedReply, reply);
                }

                @Override
                public void onError(Throwable throwable) {
                    assertNull(throwable);
                }

                @Override
                public void onCompleted() {
                    completed.compareAndSet(false, true);
                    assertTrue(completed.get());
                }
            });

            then(consumerAccountService).should(times(1)).findById(1L);
            then(consumerAccountService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_EntityNotFoundException_is_received() {
            var request = FindAccountRequest.newBuilder().setId(1L).build();
            var completed = new AtomicBoolean(false);

            given(consumerAccountService.findById(anyLong()))
                    .willThrow(new EntityNotFoundException("Account", "Id", "1"));

            accountGrpcServer.findById(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountReply reply) {
                    assertNull(reply);
                }

                @Override
                public void onError(Throwable throwable) {
                    var result = assertInstanceOf(StatusRuntimeException.class, throwable);
                    assertEquals(Status.NOT_FOUND.getCode(), result.getStatus().getCode());
                    assertEquals("Account not found by Id 1", result.getStatus().getDescription());
                }

                @Override
                public void onCompleted() {
                    assertFalse(completed.get());
                }
            });

            then(consumerAccountService).should(times(1)).findById(1L);
            then(consumerAccountService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_RuntimeException_is_received() {
            var request = FindAccountRequest.newBuilder().setId(1L).build();
            var completed = new AtomicBoolean(false);

            given(consumerAccountService.findById(anyLong())).willThrow(
                    new RuntimeException("Any runtime exception"));

            accountGrpcServer.findById(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountReply reply) {
                    assertNull(reply);
                }

                @Override
                public void onError(Throwable throwable) {
                    var result = assertInstanceOf(StatusRuntimeException.class, throwable);
                    assertEquals(Status.INTERNAL.getCode(), result.getStatus().getCode());
                    assertNotNull(result.getCause());
                }

                @Override
                public void onCompleted() {
                    assertFalse(completed.get());
                }
            });

            then(consumerAccountService).should(times(1)).findById(1L);
            then(consumerAccountService).shouldHaveNoMoreInteractions();
        }
    }
}
package com.maurigvs.bank.checkingaccount.grpc.server;

import com.maurigvs.bank.checkingaccount.exception.EntityNotFoundException;
import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import com.maurigvs.bank.checkingaccount.service.CheckingAccountService;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.CheckingAccountData;
import com.maurigvs.bank.grpc.FindCheckingAccountReply;
import com.maurigvs.bank.grpc.FindCheckingAccountRequest;
import com.maurigvs.bank.grpc.UpdateBalanceReply;
import com.maurigvs.bank.grpc.UpdateBalanceRequest;
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
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {CheckingAccountGrpcServer.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CheckingAccountGrpcServerTest {

    @Autowired
    CheckingAccountGrpcServer grpcServer;

    @MockBean
    CheckingAccountService service;

    @Nested
    class findById {

        @Test
        void should_return_Account() {
            var request = FindCheckingAccountRequest.newBuilder().setId(1L).build();
            var completed = new AtomicBoolean(false);

            var accountHolderData = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
            var accountData = CheckingAccountData.newBuilder().setId(1L).setBalance(0.0).setAccountHolderData(accountHolderData).build();
            var expectedReply = FindCheckingAccountReply.newBuilder().setCheckingAccountData(accountData).build();

            var checkingAccount = new CheckingAccount(1L,
                    LocalDate.of(2024,1,1), 123456,
                    new AccountHolder(1L, "12345"));
            given(service.findById(anyLong())).willReturn(checkingAccount);

            grpcServer.findById(request, new StreamObserver<>() {
                @Override
                public void onNext(FindCheckingAccountReply reply) {
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

            then(service).should(times(1)).findById(1L);
            then(service).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_EntityNotFoundException_is_received() {
            var request = FindCheckingAccountRequest.newBuilder().setId(1L).build();
            var completed = new AtomicBoolean(false);

            given(service.findById(anyLong()))
                    .willThrow(new EntityNotFoundException("Account", "Id", "1"));

            grpcServer.findById(request, new StreamObserver<>() {

                @Override
                public void onNext(FindCheckingAccountReply reply) {
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

            then(service).should(times(1)).findById(1L);
            then(service).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_RuntimeException_is_received() {
            var request = FindCheckingAccountRequest.newBuilder().setId(1L).build();
            var completed = new AtomicBoolean(false);

            given(service.findById(anyLong())).willThrow(
                    new RuntimeException("Any runtime exception"));

            grpcServer.findById(request, new StreamObserver<>() {

                @Override
                public void onNext(FindCheckingAccountReply reply) {
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

            then(service).should(times(1)).findById(1L);
            then(service).shouldHaveNoMoreInteractions();
        }
    }

    @Nested
    class updateBalanceById {

        @Test
        void should_return_balance_updated() {
            var request = UpdateBalanceRequest.newBuilder().setId(1L).setAmount(150.00).build();
            var expectedReply = UpdateBalanceReply.newBuilder().setId(1L).setBalance(500.00).build();
            var completed = new AtomicBoolean(false);

            var checkingAccount = new CheckingAccount(1L,
                    LocalDate.of(2024,1,1),
                    123456, new AccountHolder(1L, "12345"));
            checkingAccount.setBalance(500.00);

            given(service.updateBalanceById(anyLong(),anyDouble())).willReturn(checkingAccount);

            grpcServer.updateBalanceById(request, new StreamObserver<>() {

                @Override
                public void onNext(UpdateBalanceReply updateBalanceReply) {
                    assertEquals(expectedReply, updateBalanceReply);
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

            then(service).should(times(1)).updateBalanceById(request.getId(), request.getAmount());
            then(service).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_EntityNotFoundException_is_received() {
            var request = UpdateBalanceRequest.newBuilder().setId(1L).setAmount(150.00).build();
            var completed = new AtomicBoolean(false);

            given(service.updateBalanceById(anyLong(),anyDouble()))
                    .willThrow(new EntityNotFoundException("Account", "Id", "1"));

            grpcServer.updateBalanceById(request, new StreamObserver<>() {

                @Override
                public void onNext(UpdateBalanceReply updateBalanceReply) {
                    assertNull(updateBalanceReply);
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

            then(service).should(times(1)).updateBalanceById(request.getId(), request.getAmount());
            then(service).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_RuntimeException_is_received() {
            var request = UpdateBalanceRequest.newBuilder().setId(1L).setAmount(150.00).build();
            var completed = new AtomicBoolean(false);

            given(service.findById(anyLong())).willThrow(
                    new RuntimeException("Any runtime exception"));

            grpcServer.updateBalanceById(request, new StreamObserver<>() {

                @Override
                public void onNext(UpdateBalanceReply updateBalanceReply) {
                    assertNull(updateBalanceReply);
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

            then(service).should(times(1)).updateBalanceById(request.getId(), request.getAmount());
            then(service).shouldHaveNoMoreInteractions();
        }
    }
}
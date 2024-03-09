package com.maurigvs.bank.accountholder.grpc.server;

import com.maurigvs.bank.accountholder.grpc.server.calls.FindByTaxIdGrpcCall;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.FindAccountHolderReply;
import com.maurigvs.bank.grpc.FindAccountHolderRequest;
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

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {AccountHolderGrpcServer.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountHolderGrpcServerTest {

    @Autowired
    AccountHolderGrpcServer accountHolderGrpcServer;

    @MockBean
    FindByTaxIdGrpcCall personService;

    @Nested
    class findByTaxId {

        @Test
        void should_return_AccountHolder() {
            var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
            var accountHolderData = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
            var reply = FindAccountHolderReply.newBuilder().setAccountHolderData(accountHolderData).build();
            var completed = new AtomicBoolean(false);
            given(personService.processCall(any())).willReturn(reply);

            accountHolderGrpcServer.findByTaxId(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountHolderReply findAccountHolderReply) {
                    assertEquals(reply, findAccountHolderReply);
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

            then(personService).should(times(1)).processCall(request);
            then(personService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_NoSuchElementException_is_received() {

            var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
            var completed = new AtomicBoolean(false);
            var exception = new StatusRuntimeException(Status.NOT_FOUND.withDescription("Person not found by taxId 12345"));
            given(personService.processCall(any())).willThrow(exception);

            accountHolderGrpcServer.findByTaxId(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountHolderReply accountHolderReply) {
                    assertNull(accountHolderReply);
                }

                @Override
                public void onError(Throwable throwable) {
                    var result = assertInstanceOf(StatusRuntimeException.class, throwable);
                    assertSame(exception, result);
                }

                @Override
                public void onCompleted() {
                    assertFalse(completed.get());
                }
            });

            then(personService).should(times(1)).processCall(request);
            then(personService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_RuntimeException_is_received() {

            var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
            var completed = new AtomicBoolean(false);
            var exception = new StatusRuntimeException(Status.NOT_FOUND.withDescription("Any runtime exception"));
            given(personService.processCall(any())).willThrow(exception);

            accountHolderGrpcServer.findByTaxId(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountHolderReply accountHolderReply) {
                    assertNull(accountHolderReply);
                }

                @Override
                public void onError(Throwable throwable) {
                    var result = assertInstanceOf(StatusRuntimeException.class, throwable);
                    assertSame(exception, result);
                }

                @Override
                public void onCompleted() {
                    assertFalse(completed.get());
                }
            });

            then(personService).should(times(1)).processCall(request);
            then(personService).shouldHaveNoMoreInteractions();
        }
    }
}

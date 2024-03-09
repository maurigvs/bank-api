package com.maurigvs.bank.accountholder.grpc.server;

import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.service.PersonService;
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

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {AccountHolderGrpcServer.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountHolderGrpcServerTest {

    @Autowired
    AccountHolderGrpcServer accountHolderGrpcServer;

    @MockBean
    PersonService personService;

    @Nested
    class findByTaxId {

        @Test
        void should_return_AccountHolder() {

            var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
            var accountHolderData = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
            var expected = FindAccountHolderReply.newBuilder().setAccountHolderData(accountHolderData).build();
            var completed = new AtomicBoolean(false);

            var person = new Person(1L,
                    "12345",
                    LocalDate.of(2024,2,25),
                    "John",
                    "Snow",
                    LocalDate.of(1987,7,28));
            given(personService.findByTaxId(anyString())).willReturn(person);

            accountHolderGrpcServer.findByTaxId(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountHolderReply reply) {
                    assertEquals(expected, reply);
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

            then(personService).should(times(1)).findByTaxId(request.getTaxId());
            then(personService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_NoSuchElementException_is_received() {

            var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
            var completed = new AtomicBoolean(false);

            given(personService.findByTaxId(anyString())).willThrow(
                    new NoSuchElementException("Person not found by taxId 12345"));

            accountHolderGrpcServer.findByTaxId(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountHolderReply accountHolderReply) {
                    assertNull(accountHolderReply);
                }

                @Override
                public void onError(Throwable throwable) {
                    var result = assertInstanceOf(StatusRuntimeException.class, throwable);
                    assertEquals(Status.NOT_FOUND.getCode(), result.getStatus().getCode());
                    assertEquals("Person not found by taxId 12345", result.getStatus().getDescription());
                }

                @Override
                public void onCompleted() {
                    assertFalse(completed.get());
                }
            });

            then(personService).should(times(1)).findByTaxId(request.getTaxId());
            then(personService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_RuntimeException_is_received() {

            var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
            var completed = new AtomicBoolean(false);

            given(personService.findByTaxId(anyString())).willThrow(
                    new RuntimeException("Any runtime exception"));

            accountHolderGrpcServer.findByTaxId(request, new StreamObserver<>() {

                @Override
                public void onNext(FindAccountHolderReply accountHolderReply) {
                    assertNull(accountHolderReply);
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

            then(personService).should(times(1)).findByTaxId(request.getTaxId());
            then(personService).shouldHaveNoMoreInteractions();
        }
    }

    @Nested
    class FindAccountHolderReplyMapperTest {

        @Test
        void should_return_FindAccountHolderReply_given_an_Person() {
            var person = new Person(1L,
                    "12345",
                    LocalDate.of(2024,2,25),
                    "John",
                    "Snow",
                    LocalDate.of(1987,7,28));

            var reply = new AccountHolderGrpcServer.FindAccountHolderReplyMapper().apply(person);
            var result = reply.getAccountHolderData();

            assertEquals(person.getId(), result.getId());
            assertEquals(person.getTaxId(), result.getTaxId());
        }
    }
}

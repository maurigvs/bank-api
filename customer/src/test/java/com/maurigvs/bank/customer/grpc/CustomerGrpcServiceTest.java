package com.maurigvs.bank.customer.grpc;

import com.maurigvs.bank.CustomerReply;
import com.maurigvs.bank.CustomerRequest;
import com.maurigvs.bank.customer.exception.EntityNotFoundException;
import com.maurigvs.bank.customer.model.Person;
import com.maurigvs.bank.customer.service.PersonService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {CustomerGrpcService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerGrpcServiceTest {

    @Autowired
    CustomerGrpcService customerGrpcService;

    @MockBean
    PersonService personService;

    @Nested
    class findByTaxId {

        @Test
        void should_return_Customer() {

            var customerRequest = CustomerRequest.newBuilder().setTaxId("12345").build();
            var expectedReply = CustomerReply.newBuilder().setId(1L).setTaxId("12345").build();
            var completed = new AtomicBoolean(false);

            var person = new Person(1L,
                    "12345",
                    LocalDate.of(2024,2,25),
                    "John",
                    "Snow",
                    LocalDate.of(1987,7,28));
            given(personService.findByTaxId(anyString())).willReturn(person);

            customerGrpcService.findByTaxId(customerRequest, new StreamObserver<>() {

                @Override
                public void onNext(CustomerReply customerReply) {
                    assertEquals(expectedReply.getId(), customerReply.getId());
                    assertEquals(expectedReply.getTaxId(), customerReply.getTaxId());
                    assertEquals(expectedReply, customerReply);
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

            then(personService).should(times(1)).findByTaxId(customerRequest.getTaxId());
            then(personService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_EntityNotFoundException_is_received() {

            var customerRequest = CustomerRequest.newBuilder().setTaxId("12345").build();
            var completed = new AtomicBoolean(false);

            given(personService.findByTaxId(anyString())).willThrow(
                    new EntityNotFoundException("Person", "taxId", "12345"));

            customerGrpcService.findByTaxId(customerRequest, new StreamObserver<>() {

                @Override
                public void onNext(CustomerReply customerReply) {
                    assertNull(customerReply);
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

            then(personService).should(times(1)).findByTaxId(customerRequest.getTaxId());
            then(personService).shouldHaveNoMoreInteractions();
        }

        @Test
        void should_throw_StatusRuntimeException_when_RuntimeException_is_received() {

            var customerRequest = CustomerRequest.newBuilder().setTaxId("12345").build();
            var completed = new AtomicBoolean(false);

            given(personService.findByTaxId(anyString())).willThrow(
                    new RuntimeException("Any runtime exception"));

            customerGrpcService.findByTaxId(customerRequest, new StreamObserver<>() {

                @Override
                public void onNext(CustomerReply customerReply) {
                    assertNull(customerReply);
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

            then(personService).should(times(1)).findByTaxId(customerRequest.getTaxId());
            then(personService).shouldHaveNoMoreInteractions();
        }
    }
}
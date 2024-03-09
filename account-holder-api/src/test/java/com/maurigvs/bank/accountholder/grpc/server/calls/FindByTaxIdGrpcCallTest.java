package com.maurigvs.bank.accountholder.grpc.server.calls;

import com.maurigvs.bank.accountholder.exception.GrpcServerException;
import com.maurigvs.bank.accountholder.grpc.server.calls.FindByTaxIdGrpcCall;
import com.maurigvs.bank.accountholder.model.Person;
import com.maurigvs.bank.accountholder.service.PersonService;
import com.maurigvs.bank.grpc.AccountHolderData;
import com.maurigvs.bank.grpc.FindAccountHolderReply;
import com.maurigvs.bank.grpc.FindAccountHolderRequest;
import io.grpc.Status;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {FindByTaxIdGrpcCall.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FindByTaxIdGrpcCallTest {

    @Autowired
    FindByTaxIdGrpcCall findByTaxIdGrpcCall;

    @MockBean
    PersonService personService;

    @Test
    void processCall() {
        var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();
        var accountHolderData = AccountHolderData.newBuilder().setId(1L).setTaxId("12345").build();
        var reply = FindAccountHolderReply.newBuilder().setAccountHolderData(accountHolderData).build();

        var person = new Person(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "John",
                "Snow",
                LocalDate.of(1987,7,28));
        given(personService.findByTaxId(anyString())).willReturn(person);

        var result = findByTaxIdGrpcCall.processCall(request);

        then(personService).should().findByTaxId(request.getTaxId());
        then(personService).shouldHaveNoMoreInteractions();
        assertEquals(reply, result);
    }

    @Test
    void should_throw_StatusRuntimeException_when_NoSuchElementException_is_received() {
        var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();

        given(personService.findByTaxId(anyString())).willThrow(
                new NoSuchElementException("Person not found by taxId 12345"));

        var result = assertThrows(GrpcServerException.class, () -> findByTaxIdGrpcCall.processCall(request));

        assertEquals(Status.NOT_FOUND.getCode(), result.getStatus().getCode());
        assertEquals("Person not found by taxId 12345", result.getStatus().getDescription());
    }

    @Test
    void should_throw_StatusRuntimeException_when_RuntimeException_is_received() {
        var request = FindAccountHolderRequest.newBuilder().setTaxId("12345").build();

        given(personService.findByTaxId(anyString())).willThrow(
                new RuntimeException("Any runtime exception"));

        var result = assertThrows(GrpcServerException.class, () -> findByTaxIdGrpcCall.processCall(request));

        assertEquals(Status.INTERNAL.getCode(), result.getStatus().getCode());
        assertEquals("Any runtime exception", result.getStatus().getDescription());
        assertNotNull(result.getCause());
    }
}
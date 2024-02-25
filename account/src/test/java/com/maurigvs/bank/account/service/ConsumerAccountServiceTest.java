package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.exception.EntityNotFoundException;
import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.repository.ConsumerAccountRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {ConsumerAccountService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConsumerAccountServiceTest {

    @Autowired
    ConsumerAccountService service;

    @MockBean
    ConsumerAccountRepository repository;

    @Test
    void should_create_ConsumerAccount() {
        var account = new ConsumerAccount(null, "12345", LocalDate.now());

        service.openAccount(account);

        verify(repository).save(account);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_return_ConsumerAccount_list() {
        var account = new ConsumerAccount(1L, "12345", LocalDate.now());
        given(repository.findAll()).willReturn(List.of(account));

        service.findAllAccounts();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_delete_ConsumerAccount_by_Id() {
        var id = 1L;
        var account = new ConsumerAccount(1L, "12345", LocalDate.now());
        given(repository.findById(anyLong())).willReturn(Optional.of(account));

        service.closeAccount(id);

        verify(repository).findById(id);
        verify(repository).delete(account);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_throw_EntityNotFoundException_when_ConsumerAccount_not_found_by_Id() {
        var id = 1L;
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class,
                () -> service.closeAccount(id));

        assertEquals("Account not found by Id 1", exception.getMessage());
    }
}
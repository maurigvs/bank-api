package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.exception.EntityNotFoundException;
import com.maurigvs.bank.account.model.ConsumerAccount;
import com.maurigvs.bank.account.model.Customer;
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
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {ConsumerAccountService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConsumerAccountServiceTest {

    @Autowired
    ConsumerAccountService accountService;

    @MockBean
    ConsumerAccountRepository accountRepository;

    @Test
    void should_create_ConsumerAccount() {
        var customer = new Customer(1L, "123456");
        var account = new ConsumerAccount(null, LocalDate.now(), 123456, customer);

        accountService.openAccount(account);

        then(accountRepository).should().save(account);
        then(accountRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_ConsumerAccount_list() {
        var customer = new Customer(1L, "123456");
        var account = new ConsumerAccount(1L, LocalDate.now(), 123456, customer);
        given(accountRepository.findAll()).willReturn(List.of(account));

        accountService.findAllAccounts();

        then(accountRepository).should().findAll();
        then(accountRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_delete_ConsumerAccount_by_Id() {
        var id = 1L;
        var customer = new Customer(1L, "123456");
        var account = new ConsumerAccount(1L, LocalDate.now(), 12345, customer);
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(account));

        accountService.closeAccount(id);

        then(accountRepository).should().findById(id);
        then(accountRepository).should().delete(account);
        then(accountRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_throw_EntityNotFoundException_when_ConsumerAccount_not_found_by_Id() {
        var id = 1L;
        given(accountRepository.findById(anyLong())).willReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> accountService.closeAccount(id));

        assertEquals("Account not found by Id 1", exception.getMessage());
    }
}

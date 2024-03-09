package com.maurigvs.bank.checkingaccount.service;

import com.maurigvs.bank.checkingaccount.model.AccountHolder;
import com.maurigvs.bank.checkingaccount.model.CheckingAccount;
import com.maurigvs.bank.checkingaccount.repository.CheckingAccountRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {CheckingAccountService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CheckingAccountServiceTest {

    @Autowired
    CheckingAccountService service;

    @MockBean
    CheckingAccountRepository repository;

    @Test
    void should_create_CheckingAccount() {
        var accountHolder = new AccountHolder(1L, "123456");
        var checkingAccount = new CheckingAccount(null, LocalDate.now(), 123456, accountHolder);

        service.openAccount(checkingAccount);

        then(repository).should().save(checkingAccount);
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_CheckingAccount_list() {
        var accountHolder = new AccountHolder(1L, "123456");
        var checkingAccount = new CheckingAccount(1L, LocalDate.now(), 123456, accountHolder);
        given(repository.findAll()).willReturn(List.of(checkingAccount));

        service.findAllAccounts();

        then(repository).should().findAll();
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Nested
    class findById {

        @Test
        void should_return_CheckingAccount_when_find_by_Id() {
            var id = 1L;
            var accountHolder = new AccountHolder(1L, "123456");
            var checkingAccount = new CheckingAccount(1L, LocalDate.now(), 12345, accountHolder);
            given(repository.findById(anyLong())).willReturn(Optional.of(checkingAccount));

            var result = service.findById(id);

            then(repository).should().findById(id);
            then(repository).shouldHaveNoMoreInteractions();
            assertSame(result, checkingAccount);
        }

        @Test
        void should_throw_EntityNotFoundException_when_CheckingAccount_not_found_by_Id() {
            given(repository.findById(anyLong())).willReturn(Optional.empty());

            var exception = assertThrows(NoSuchElementException.class, () -> service.findById(1L));

            assertEquals("Account not found by Id 1", exception.getMessage());
        }
    }

    @Nested
    class updateBalanceById {
        @Test
        void should_delete_CheckingAccount_by_Id() {
            var id = 1L;
            var amount = 150.00;
            var accountHolder = new AccountHolder(1L, "123456");
            var checkingAccount = new CheckingAccount(1L, LocalDate.now(), 12345, accountHolder);
            given(repository.getReferenceById(anyLong())).willReturn(checkingAccount);

            var result = service.updateBalanceById(id,amount);

            then(repository).should().getReferenceById(id);
            then(repository).shouldHaveNoMoreInteractions();
            assertEquals(150.00, result.getBalance());
        }
    }

    @Nested
    class closeAccount {

        @Test
        void should_delete_CheckingAccount_by_Id() {
            var id = 1L;
            var accountHolder = new AccountHolder(1L, "123456");
            var checkingAccount = new CheckingAccount(1L, LocalDate.now(), 12345, accountHolder);
            given(repository.findById(anyLong())).willReturn(Optional.of(checkingAccount));

            service.closeAccount(id);

            then(repository).should().findById(id);
            then(repository).should().delete(checkingAccount);
            then(repository).shouldHaveNoMoreInteractions();
        }
    }
}

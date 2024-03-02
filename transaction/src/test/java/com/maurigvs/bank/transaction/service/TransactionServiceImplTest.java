package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.model.Customer;
import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.repository.AccountRepository;
import com.maurigvs.bank.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {TransactionServiceImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionServiceImplTest {

    @Autowired
    TransactionService transactionService;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    AccountRepository accountRepository;

    @Test
    void should_create_new_transaction() {
        var customer = new Customer(1L);
        var account = new Account(1L, customer);
        var transaction = new Transaction(null, LocalDateTime.now(), "Initial deposit", 150.00, account);

        transactionService.create(transaction);

        then(transactionRepository).should(times(1)).save(transaction);
        then(transactionRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_List_when_find_by_account_id() {
        var account = new Account(1L, new Customer(1L));
        var transaction = new Transaction(1L,
                LocalDateTime.of(2024,2,27,15,12),
                "Initial deposit",
                150.00, account);
        account.getTransactionList().add(transaction);
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(account));

        var result = transactionService.findByAccountId(1L);

        then(accountRepository).should(times(1)).findById(1L);
        then(accountRepository).shouldHaveNoMoreInteractions();
        then(transactionRepository).shouldHaveNoInteractions();
        assertSame(account.getTransactionList(), result);
    }
}
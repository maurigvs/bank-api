package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.Account;
import com.maurigvs.bank.transaction.model.Customer;
import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {TransactionServiceImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TransactionServiceImplTest {

    @Autowired
    TransactionService service;

    @MockBean
    TransactionRepository repository;

    @Test
    void should_create_new_transaction() {
        var customer = new Customer(1L);
        var account = new Account(1L, customer);
        var transaction = new Transaction(null, LocalDateTime.now(), "Initial deposit", 150.00, account);

        service.create(transaction);

        then(repository).should(times(1)).save(transaction);
        then(repository).shouldHaveNoMoreInteractions();
    }
}
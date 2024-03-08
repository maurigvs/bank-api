package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.model.CheckingAccount;
import com.maurigvs.bank.transaction.model.AccountHolder;
import com.maurigvs.bank.transaction.model.Transaction;
import com.maurigvs.bank.transaction.repository.CheckingAccountRepository;
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
    TransactionService service;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    CheckingAccountRepository checkingAccountRepository;

    @Test
    void should_create_new_transaction() {
        var accountHolder = new AccountHolder(1L);
        var checkingAccount = new CheckingAccount(1L, accountHolder);
        var transaction = new Transaction(null, LocalDateTime.now(), "Initial deposit", 150.00, checkingAccount);

        service.create(transaction);

        then(transactionRepository).should(times(1)).save(transaction);
        then(transactionRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_List_when_find_by_CheckingAccount_id() {
        var checkingAccount = new CheckingAccount(1L, new AccountHolder(1L));
        var transaction = new Transaction(1L,
                LocalDateTime.of(2024,2,27,15,12),
                "Initial deposit",
                150.00, checkingAccount);
        checkingAccount.getTransactionList().add(transaction);
        given(checkingAccountRepository.findById(anyLong())).willReturn(Optional.of(checkingAccount));

        var result = service.findByCheckingAccountId(1L);

        then(checkingAccountRepository).should(times(1)).findById(1L);
        then(checkingAccountRepository).shouldHaveNoMoreInteractions();
        then(transactionRepository).shouldHaveNoInteractions();
        assertSame(checkingAccount.getTransactionList(), result);
    }
}
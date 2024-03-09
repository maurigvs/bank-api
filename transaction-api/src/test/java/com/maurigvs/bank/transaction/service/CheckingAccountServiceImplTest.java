package com.maurigvs.bank.transaction.service;

import com.maurigvs.bank.transaction.grpc.client.CheckingAccountGrpcClient;
import com.maurigvs.bank.transaction.model.AccountHolder;
import com.maurigvs.bank.transaction.model.CheckingAccount;
import com.maurigvs.bank.transaction.repository.CheckingAccountRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {CheckingAccountServiceImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CheckingAccountServiceImplTest {

    @Autowired
    CheckingAccountService service;

    @MockBean
    CheckingAccountRepository repository;

    @MockBean
    CheckingAccountGrpcClient grpcClient;

    @Nested
    class findById {

        @Test
        void should_return_CheckingAccount_from_repository_when_exists() {
            var checkingAccount = new CheckingAccount(1L, new AccountHolder(1L));
            given(repository.findById(anyLong())).willReturn(Optional.of(checkingAccount));

            var result = service.findById(1L);

            then(repository).should().findById(1L);
            then(repository).shouldHaveNoMoreInteractions();
            then(grpcClient).shouldHaveNoInteractions();
            assertSame(checkingAccount, result);
        }

        @Test
        void should_return_CheckingAccount_from_Grpc_after_save_to_repository() {
            var checkingAccount = new CheckingAccount(1L, new AccountHolder(1L));
            given(repository.findById(anyLong())).willReturn(Optional.empty());
            given(grpcClient.findById(anyLong())).willReturn(checkingAccount);
            given(repository.save(any())).willReturn(checkingAccount);

            var result = service.findById(1L);

            then(repository).should().findById(1L);
            then(grpcClient).should().findById(1L);
            then(repository).should().save(checkingAccount);
            then(grpcClient).shouldHaveNoMoreInteractions();
            then(repository).shouldHaveNoMoreInteractions();
            assertSame(checkingAccount, result);
        }
    }
}

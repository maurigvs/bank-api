package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.account.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {CustomerServiceImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerServiceImplTest {

    @Autowired
    CustomerService service;

    @MockBean
    CustomerRepository repository;

    @Test
    void should_return_Customer_given_an_TaxId() {
        var taxId = "123456";
        var customer = new Customer(1L, "123456");
        given(repository.existsByTaxId(anyString())).willReturn(false);
        given(repository.findByTaxId(any())).willReturn(Optional.of(customer));

        var result = service.findByTaxId(taxId);

        verify(repository).existsByTaxId(taxId);
        verify(repository).save(any(Customer.class));
        verify(repository).findByTaxId(taxId);
        verifyNoMoreInteractions(repository);
        assertSame(customer, result);
    }
}

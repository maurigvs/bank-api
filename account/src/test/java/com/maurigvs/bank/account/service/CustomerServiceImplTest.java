package com.maurigvs.bank.account.service;

import com.maurigvs.bank.account.grpc.CustomerApiService;
import com.maurigvs.bank.account.model.Customer;
import com.maurigvs.bank.account.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertSame;
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

    @MockBean
    CustomerApiService apiService;

    @Test
    void should_return_Customer_given_an_TaxId() {
        var taxId = "123456";
        var customer = new Customer(1L, "123456");
        given(apiService.findByTaxId(anyString())).willReturn(customer);

        var result = service.findByTaxId(taxId);

        assertSame(customer, result);
        verify(apiService).findByTaxId(taxId);
        verify(repository).save(customer);
        verifyNoMoreInteractions(apiService, repository);
    }
}

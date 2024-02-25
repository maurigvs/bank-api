package com.maurigvs.bank.customer.service;

import com.maurigvs.bank.customer.exception.EntityNotFoundException;
import com.maurigvs.bank.customer.model.Company;
import com.maurigvs.bank.customer.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {CompanyService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CompanyServiceTest {

    @Autowired
    CompanyService service;

    @MockBean
    CompanyRepository repository;

    @Test
    void should_create_Company() {
        var company = new Company(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "Company",
                "Company Inc.",
                LocalDate.of(2014,1,1));

        service.create(company);

        then(repository).should().save(company);
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void should_return_Company_by_Id() {
        var taxId = "12345";
        var company = new Company(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "Company",
                "Company Inc.",
                LocalDate.of(2014,1,1));
        given(repository.findByTaxId(anyString())).willReturn(Optional.of(company));

        var result = service.findByTaxId(taxId);

        then(repository).should().findByTaxId(taxId);
        then(repository).shouldHaveNoMoreInteractions();
        assertSame(company, result);
    }

    @Test
    void should_throw_EntityNotFoundException_when_Company_not_found_by_Id() {
        var taxId = "12345";
        given(repository.findByTaxId(anyString())).willReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> service.findByTaxId(taxId));

        assertEquals("Company not found by taxId 12345", exception.getMessage());
    }

    @Test
    void should_return_Company_list() {
        var company = new Company(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "Company",
                "Company Inc.",
                LocalDate.of(2014,1,1));
        var companyList = List.of(company);
        given(repository.findAll()).willReturn(companyList);

        var result = service.findAll();

        then(repository).should().findAll();
        then(repository).shouldHaveNoMoreInteractions();
        assertSame(companyList, result);
    }

    @Test
    void should_delete_Company_by_Id() {
        var id = 1L;
        var company = new Company(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "Company",
                "Company Inc.",
                LocalDate.of(2014,1,1));
        given(repository.findById(anyLong())).willReturn(Optional.of(company));

        service.deleteById(id);

        then(repository).should().findById(id);
        then(repository).should().delete(company);
        then(repository).shouldHaveNoMoreInteractions();
    }
}
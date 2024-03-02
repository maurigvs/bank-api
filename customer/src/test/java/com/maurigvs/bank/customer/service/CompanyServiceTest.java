package com.maurigvs.bank.customer.service;

import com.maurigvs.bank.customer.exception.EntityNotFoundException;
import com.maurigvs.bank.customer.model.Company;
import com.maurigvs.bank.customer.repository.CompanyRepository;
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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = {CompanyService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CompanyServiceTest {

    @Autowired
    CompanyService companyService;

    @MockBean
    CompanyRepository companyRepository;

    @Test
    void should_create_Company() {
        var company = new Company(1L,
                "12345",
                LocalDate.of(2024,2,25),
                "Company",
                "Company Inc.",
                LocalDate.of(2014,1,1));

        companyService.create(company);

        then(companyRepository).should().save(company);
        then(companyRepository).shouldHaveNoMoreInteractions();
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
        given(companyRepository.findByTaxId(anyString())).willReturn(Optional.of(company));

        var result = companyService.findByTaxId(taxId);

        then(companyRepository).should().findByTaxId(taxId);
        then(companyRepository).shouldHaveNoMoreInteractions();
        assertSame(company, result);
    }

    @Test
    void should_throw_EntityNotFoundException_when_Company_not_found_by_Id() {
        var taxId = "12345";
        given(companyRepository.findByTaxId(anyString())).willReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> companyService.findByTaxId(taxId));

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
        given(companyRepository.findAll()).willReturn(companyList);

        var result = companyService.findAll();

        then(companyRepository).should().findAll();
        then(companyRepository).shouldHaveNoMoreInteractions();
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
        given(companyRepository.findById(anyLong())).willReturn(Optional.of(company));

        companyService.deleteById(id);

        then(companyRepository).should().findById(id);
        then(companyRepository).should().delete(company);
        then(companyRepository).shouldHaveNoMoreInteractions();
    }
}
package com.maurigvs.bank.customer.controller;

import com.maurigvs.bank.customer.dto.CompanyRequest;
import com.maurigvs.bank.customer.dto.CompanyResponse;
import com.maurigvs.bank.customer.mapper.CompanyMapper;
import com.maurigvs.bank.customer.mapper.CompanyResponseMapper;
import com.maurigvs.bank.customer.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCustomer(@RequestBody CompanyRequest request){
        var company = new CompanyMapper().apply(request);
        service.create(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@PathVariable Long id,
                               @RequestBody CompanyRequest request){
        var company = new CompanyMapper().apply(request);
        service.updateById(id, company);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyResponse> getAllCustomers(){
        return service.findAll().stream().map(new CompanyResponseMapper()).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerById(@PathVariable Long id){
        service.deleteById(id);
    }
}

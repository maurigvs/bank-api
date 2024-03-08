package com.maurigvs.bank.accountholder.controller;

import com.maurigvs.bank.accountholder.dto.PersonRequest;
import com.maurigvs.bank.accountholder.dto.PersonResponse;
import com.maurigvs.bank.accountholder.mapper.PersonMapper;
import com.maurigvs.bank.accountholder.mapper.PersonResponseMapper;
import com.maurigvs.bank.accountholder.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody @Valid PersonRequest request){
        var person = new PersonMapper().apply(request);
        service.create(person);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResponse> getAllPersons(){
        return service.findAll().stream().map(new PersonResponseMapper()).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePersonById(@PathVariable Long id){
        service.deleteById(id);
    }
}

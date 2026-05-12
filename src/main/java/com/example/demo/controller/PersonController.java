package com.example.demo.controller;


import com.example.demo.dto.PersonRequest;
import com.example.demo.dto.PersonResponse;
import com.example.demo.model.Person;
import com.example.demo.model.Product;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAll() {
        return personService.findAllPeople();
    }






    @PostMapping
    public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonRequest request) {

        Person person = new Person();
        person.setName(request.getName());
        person.setAge(request.getAge());


        Person saved = personService.create(person);

        PersonResponse response = new PersonResponse(
                saved.getName(),
                saved.getAge()
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getOne(@PathVariable Long id) {
        Person found = personService.getOne(id);
        PersonResponse response = new PersonResponse(
                found.getName(),
                found.getAge()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
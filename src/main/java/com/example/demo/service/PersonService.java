package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAllPeople() {
        return personRepository.findAll();
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Osoba o id=" + id + " nie istnieje"
            );
        }
        personRepository.deleteById(id);
    }


    public Person getOne(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nie znaleziono osoby o ID: " + id
                ));
    }


}
package com.test.client.service;

import com.test.client.domain.Person;
import com.test.client.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        return this.personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return this.personRepository.findById(id);
    }

    @Override
    public Person savePerson(Person person) {
        return this.personRepository.save(person);
    }

    @Override
    public void deletePersonById(Long id) {
        this.personRepository.deleteById(id);
    }
}

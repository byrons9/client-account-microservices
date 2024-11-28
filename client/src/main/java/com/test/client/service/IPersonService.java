package com.test.client.service;

import com.test.client.domain.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    List<Person> getAllPersons();
    Optional<Person> getPersonById(Long id);
    Person savePerson(Person person);
    void deletePersonById(Long id);
}

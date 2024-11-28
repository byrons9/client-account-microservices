package com.test.client.service;

import com.test.client.domain.Person;
import com.test.client.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPersons() {

        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setIdentification("1234567890");
        person.setAddress("123 Main St, Anytown USA");
        person.setPhone("123-456-7890");
        person.setGender("M");
        person.setAge(30);

        Person person2 = new Person();
        person.setId(2L);
        person.setName("Jane Doe");
        person.setIdentification("0987654321");
        person.setAddress("456 Main St, Anytown USA");
        person.setPhone("098-765-4321");
        person.setGender("F");
        person.setAge(25);


        List<Person> expectedPersons = List.of(person, person2);

        Mockito.when(personRepository.findAll()).thenReturn(expectedPersons);

        List<Person> response = personService.getAllPersons();


        Assertions.assertThat(response).isEqualTo(expectedPersons).hasSize(2);

        Mockito.verify(personRepository, Mockito.times(1)).findAll();

    }

    @Test
    void testGetPersonById() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setIdentification("1234567890");
        person.setAddress("123 Main St, Anytown USA");
        person.setPhone("123-456-7890");
        person.setGender("M");
        person.setAge(30);

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Person response = personService.getPersonById(1L).get();

        Assertions.assertThat(response).isEqualTo(person);

        Mockito.verify(personRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetEmptyPersonById() {
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThat(personService.getPersonById(1L)).isEmpty();

        Mockito.verify(personRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testSavePerson() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setIdentification("1234567890");
        person.setAddress("123 Main St, Anytown USA");
        person.setPhone("123-456-7890");
        person.setGender("M");
        person.setAge(30);

        Mockito.when(personRepository.save(person)).thenReturn(person);

        Person response = personService.savePerson(person);

        Assertions.assertThat(response).isEqualTo(person);

        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }

    @Test
    void testExpectedErrorSavePerson() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setIdentification("1234567890");
        person.setAddress("123 Main St, Anytown USA");
        person.setPhone("123-456-7890");
        person.setGender("M");
        person.setAge(30);

        Mockito.when(personRepository.save(person)).thenThrow(new RuntimeException("Error saving person"));

        Assertions.assertThatThrownBy(() -> personService.savePerson(person))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error saving person");

        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }

    @Test
    void testDeletePersonById() {
        personService.deletePersonById(1L);
        Mockito.verify(personRepository, Mockito.times(1)).deleteById(1L);
    }



}

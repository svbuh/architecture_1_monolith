package de.svenbuhre.lalarchitecture1monolith.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.svenbuhre.lalarchitecture1monolith.model.Person;
import de.svenbuhre.lalarchitecture1monolith.model.Planet;
import de.svenbuhre.lalarchitecture1monolith.repository.PersonRepository;
import de.svenbuhre.lalarchitecture1monolith.repository.PlanetRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SwapiServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private PlanetRepository planetRepository;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private SwapiService swapiService;

    @BeforeEach
    public void setUp() {
        reset(restTemplate, planetRepository, personRepository);
    }

    @Test
    void testGetPlanetsFromRepository() {
        Planet planet1 = new Planet();
        planet1.setName("Tatooine");
        Planet planet2 = new Planet();
        planet2.setName("Alderaan");
        List<Planet> planets = Arrays.asList(planet1, planet2);

        when(planetRepository.count()).thenReturn(2L);
        when(planetRepository.findAll()).thenReturn(planets);

        List<Planet> result = swapiService.getPlanets();

        assertEquals(2, result.size());
        verify(planetRepository, times(1)).findAll();
        verify(restTemplate, never()).getForObject(anyString(), any());
    }

    @Test
    void testGetPlanetsFromApi() {
        when(planetRepository.count()).thenReturn(0L);

        SwapiService.PlanetResponse planetResponse1 = new SwapiService.PlanetResponse();
        Planet planet1 = new Planet();
        planet1.setName("Tatooine");
        planetResponse1.setResults(List.of(planet1));
        planetResponse1.setNext("nextUrl");

        SwapiService.PlanetResponse planetResponse2 = new SwapiService.PlanetResponse();
        Planet planet2 = new Planet();
        planet2.setName("Alderaan");
        planetResponse2.setResults(List.of(planet2));
        planetResponse2.setNext(null);

        when(restTemplate.getForObject(anyString(), eq(SwapiService.PlanetResponse.class)))
                .thenReturn(planetResponse1)
                .thenReturn(planetResponse2);

        List<Planet> result = swapiService.getPlanets();

        assertEquals(2, result.size());
        verify(planetRepository, times(1)).count();
        verify(restTemplate, times(2)).getForObject(anyString(), eq(SwapiService.PlanetResponse.class));
    }

    @Test
    void testGetPeopleFromRepository() {
        Person person1 = new Person();
        person1.setName("Luke Skywalker");
        Person person2 = new Person();
        person2.setName("Leia Organa");
        List<Person> people = Arrays.asList(person1, person2);

        when(personRepository.count()).thenReturn(2L);
        when(personRepository.findAll()).thenReturn(people);

        List<Person> result = swapiService.getPeople();

        assertEquals(2, result.size());
        verify(personRepository, times(1)).findAll();
        verify(restTemplate, never()).getForObject(anyString(), any());
    }

    @Test
    void testGetPeopleFromApi() {
        when(personRepository.count()).thenReturn(0L);

        SwapiService.PersonResponse personResponse1 = new SwapiService.PersonResponse();
        Person person1 = new Person();
        person1.setName("Luke Skywalker");
        personResponse1.setResults(List.of(person1));
        personResponse1.setNext("nextUrl");

        SwapiService.PersonResponse personResponse2 = new SwapiService.PersonResponse();
        Person person2 = new Person();
        person2.setName("Leia Organa");
        personResponse2.setResults(List.of(person2));
        personResponse2.setNext(null);

        when(restTemplate.getForObject(anyString(), eq(SwapiService.PersonResponse.class)))
                .thenReturn(personResponse1)
                .thenReturn(personResponse2);

        List<Person> result = swapiService.getPeople();

        assertEquals(2, result.size());
        verify(personRepository, times(1)).count();
        verify(restTemplate, times(2)).getForObject(anyString(), eq(SwapiService.PersonResponse.class));
    }
}
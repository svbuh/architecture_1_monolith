package de.svenbuhre.lalarchitecture1monolith.service;

import de.svenbuhre.lalarchitecture1monolith.model.Person;
import de.svenbuhre.lalarchitecture1monolith.model.Planet;
import de.svenbuhre.lalarchitecture1monolith.repository.PersonRepository;
import de.svenbuhre.lalarchitecture1monolith.repository.PlanetRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class SwapiService {
    private static final String SWAPI_BASE_URL = "https://swapi.dev/api/";
    private final RestTemplate restTemplate;

    private final PlanetRepository planetRepository;

    private final PersonRepository personRepository;

    public SwapiService(RestTemplate restTemplate, PlanetRepository planetRepository, PersonRepository personRepository) {
        this.restTemplate = restTemplate;
        this.planetRepository = planetRepository;
        this.personRepository = personRepository;
    }

    @PostConstruct
    @Transactional
    public void initDatabase() {
        if (planetRepository.count() == 0) {
            planetRepository.saveAll(getPlanets());
        }
        if (personRepository.count() == 0) {
            personRepository.saveAll(getPeople());
        }
    }

    public List<Planet> getPlanets() {
        if (planetRepository.count() > 0) {
            return planetRepository.findAll();
        }
        return fetchAllPages(SWAPI_BASE_URL + "planets/", PlanetResponse.class, BaseApiResponse::getResults);
    }

    public List<Person> getPeople() {
        if (personRepository.count() > 0) {
            return personRepository.findAll();
        }
        return fetchAllPages(SWAPI_BASE_URL + "people/", PersonResponse.class, BaseApiResponse::getResults);
    }

    private <T> List<T> fetchAllPages(
            String initialUrl,
            Class<? extends BaseApiResponse<T>> responseType, Function<BaseApiResponse<T>, List<T>> resultsExtractor) {
        List<T> allResults = new ArrayList<>();
        String url = initialUrl;

        while (url != null) {
            BaseApiResponse<T> response = restTemplate.getForObject(url, responseType);
            if (response != null && resultsExtractor.apply(response) != null) {
                allResults.addAll(resultsExtractor.apply(response));
                url = response.getNext();
            } else {
                url = null;
            }
        }

        return allResults;
    }

    static class PlanetResponse extends BaseApiResponse<Planet> {}
    static class PersonResponse extends BaseApiResponse<Person> {}

    @Setter
    @Getter
    private abstract static class BaseApiResponse<T> {
        private List<T> results;
        private String next;

    }
}

package de.svenbuhre.lalarchitecture1monolith.controller;

import de.svenbuhre.lalarchitecture1monolith.model.Person;
import de.svenbuhre.lalarchitecture1monolith.model.Planet;
import de.svenbuhre.lalarchitecture1monolith.service.SwapiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SwapiController {

    private final SwapiService swapiService;

    @GetMapping("/planets")
    public List<Planet> getPlanets() {
        return swapiService.getPlanets();
    }

    @GetMapping("/people")
    public List<Person> getPeople() {
        return swapiService.getPeople();
    }
}

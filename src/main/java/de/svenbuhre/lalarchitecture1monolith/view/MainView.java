package de.svenbuhre.lalarchitecture1monolith.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import de.svenbuhre.lalarchitecture1monolith.model.Person;
import de.svenbuhre.lalarchitecture1monolith.model.Planet;
import de.svenbuhre.lalarchitecture1monolith.service.SwapiService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {

    private final transient SwapiService swapiService;

    private final Grid<Planet> planetGrid;
    private final Grid<Person> personGrid;

    @Autowired
    public MainView(SwapiService swapiService) {
        this.swapiService = swapiService;
        planetGrid = new Grid<>(Planet.class);
        personGrid = new Grid<>(Person.class);

        Button showPlanetsButton = new Button("Show Planets", e -> showPlanets());
        Button showPeopleButton = new Button("Show People", e -> showPeople());

        add(showPlanetsButton, showPeopleButton, planetGrid, personGrid);
    }

    @PostConstruct
    public void init() {
        planetGrid.setVisible(false);
        personGrid.setVisible(false);
    }

    private void showPlanets() {
        List<Planet> planets = swapiService.getPlanets();
        planetGrid.setItems(planets);
        planetGrid.setVisible(true);
        personGrid.setVisible(false);
    }

    private void showPeople() {
        List<Person> people = swapiService.getPeople();
        personGrid.setItems(people);
        personGrid.setVisible(true);
        planetGrid.setVisible(false);
    }
}

package de.svenbuhre.lalarchitecture1monolith.repository;

import de.svenbuhre.lalarchitecture1monolith.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
}

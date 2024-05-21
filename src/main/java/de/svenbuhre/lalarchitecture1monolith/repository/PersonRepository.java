package de.svenbuhre.lalarchitecture1monolith.repository;

import de.svenbuhre.lalarchitecture1monolith.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
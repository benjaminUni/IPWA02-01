package de.iubh.webanwendungen.repository;

import de.iubh.webanwendungen.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
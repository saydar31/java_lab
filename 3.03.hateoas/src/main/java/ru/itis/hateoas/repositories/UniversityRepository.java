package ru.itis.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.models.University;

public interface UniversityRepository extends JpaRepository<University,Long> {
}

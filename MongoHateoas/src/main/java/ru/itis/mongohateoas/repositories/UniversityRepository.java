package ru.itis.mongohateoas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.mongohateoas.model.University;

public interface UniversityRepository extends MongoRepository<University, String> {
}

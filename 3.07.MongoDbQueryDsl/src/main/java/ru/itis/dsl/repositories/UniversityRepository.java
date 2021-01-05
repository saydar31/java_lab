package ru.itis.dsl.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.dsl.model.University;

public interface UniversityRepository extends MongoRepository<University, String> {
}

package ru.itis.springmongojpa.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.springmongojpa.model.University;

public interface UniversityRepository extends MongoRepository<University,String> {
}

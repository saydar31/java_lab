package ru.itis.mongohateoas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.mongohateoas.model.Country;

import java.util.List;

public interface CountryRepository extends MongoRepository<Country,String> {
    @RestResource(path = "name", rel = "name")
    List<Country> findAllByName(String name);

}

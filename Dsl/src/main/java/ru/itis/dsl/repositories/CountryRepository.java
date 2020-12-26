package ru.itis.dsl.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.dsl.model.Country;

import java.util.List;

public interface CountryRepository extends MongoRepository<Country,String> {
    List<Country> findAllByName(String name);

}

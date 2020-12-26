package ru.itis.dsl.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.dsl.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}

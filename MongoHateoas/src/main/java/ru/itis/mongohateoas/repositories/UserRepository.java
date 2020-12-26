package ru.itis.mongohateoas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.mongohateoas.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}

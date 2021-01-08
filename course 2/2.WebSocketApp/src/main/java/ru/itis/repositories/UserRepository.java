package ru.itis.repositories;

import ru.itis.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByName(String name);
}

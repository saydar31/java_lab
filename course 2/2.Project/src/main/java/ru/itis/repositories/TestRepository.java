package ru.itis.repositories;

import ru.itis.model.Test;

import java.util.Optional;

public interface TestRepository extends CrudRepository<Test, Long> {
    public Optional<Test> findByName(String name);
}

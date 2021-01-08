package ru.itis.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    void save(T t);

    void delete(T t);

    void update(T t);

    List<T> getAll();

    Optional<T> find(K key);
}

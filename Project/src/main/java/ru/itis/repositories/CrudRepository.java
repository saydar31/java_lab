package ru.itis.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {

    void save(T entity);

    Optional<T> find(K key);

    List<T> getAll();

    void update(T entity);

    void delete(T entity);

}

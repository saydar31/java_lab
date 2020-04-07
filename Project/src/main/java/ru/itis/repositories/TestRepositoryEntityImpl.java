package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class TestRepositoryEntityImpl implements TestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Test entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<Test> find(Long key) {
        return Optional.ofNullable(entityManager.find(Test.class, key));
    }

    @Override
    @Transactional
    public List<Test> getAll() {
        return entityManager.createQuery("select test from Test test order by test.id",Test.class).getResultList();
    }

    @Override
    @Transactional
    public void update(Test entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(Test entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional
    public Optional<Test> findByName(String name) {
        Test test = entityManager.createQuery("select test from Test test where test.name=:name", Test.class).setParameter("name", name).getSingleResult();
        return Optional.ofNullable(test);
    }
}

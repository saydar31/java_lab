package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryEntityManagerImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<User> findByName(String name) {
        try {
            return Optional.of(entityManager.createQuery("select user from User user where user.userName=:name", User.class).setParameter("name", name).getSingleResult());
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    @Transactional
    public void update(User user) {
        User updatedUser = entityManager.merge(user);
        user.setPasswordHash(updatedUser.getPasswordHash());
        user.setUserName(updatedUser.getUserName());
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return entityManager.createQuery("select user from User user", User.class).getResultList();
    }

    @Override
    @Transactional
    public Optional<User> find(Long key) {
        return Optional.of(entityManager.createQuery("select user from User user where user.id=:id", User.class)
                .setParameter("id", key)
                .getSingleResult());
    }
}

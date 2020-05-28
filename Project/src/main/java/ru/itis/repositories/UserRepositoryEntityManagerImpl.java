package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component(value = "userRepositoryJpaImpl")
public class UserRepositoryEntityManagerImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<User> getUsers(int page, int size) {
        return entityManager.createQuery("select usr from User usr order by usr.id", User.class).setFirstResult(size * page).setMaxResults(size).getResultList();
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        User user = (User) entityManager.createQuery("select usr from User usr where usr.email = :email").setParameter("email", email).getSingleResult();
        return Optional.of(user);
    }

    @Override
    @Transactional
    public void makeProofed(User user) {
        user.setProofed(true);
        entityManager.merge(user);
    }


    @Override
    @Transactional
    public void save(User entity) {
        entityManager.persist(entity);
    }


    @Override
    @Transactional
    public Optional<User> find(Long key) {
        return Optional.ofNullable(entityManager.find(User.class, key));
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return entityManager.createQuery("select usr from User usr order by usr.id", User.class).getResultList();
    }

    @Override
    @Transactional
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(User entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Transactional
    public UserDto getOneDtoById(Long id) {
        return entityManager.createQuery("select new ru.itis.dto.UserDto(user.id,user.firstName,user.lastName,user.email,user.proofed) from User user where user.id=:id", UserDto.class).setParameter("id",id).getSingleResult();
    }
}

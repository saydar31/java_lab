package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.SupportChatDialog;
import ru.itis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class SupportChatDialogRepositoryEntityManagerImpl implements SupportChatDialogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(SupportChatDialog entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<SupportChatDialog> find(Long key) {
        return Optional.ofNullable(entityManager.find(SupportChatDialog.class, key));
    }

    @Override
    @Transactional
    public List<SupportChatDialog> getAll() {
        return entityManager.createQuery("select scd from SupportChatDialog scd", SupportChatDialog.class).getResultList();
    }

    @Override
    @Transactional
    public void update(SupportChatDialog entity) {
        SupportChatDialog merged = entityManager.merge(entity);
        entity.setContainsUnreadMessages(merged.isContainsUnreadMessages());
        merged.setMessages(merged.getMessages());
    }

    @Override
    @Transactional
    public void delete(SupportChatDialog entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional
    public Optional<SupportChatDialog> findDialogByUser(User user) {
        try {
            return Optional.ofNullable(entityManager.createQuery("select scd from SupportChatDialog scd where scd.client=:user", SupportChatDialog.class).setParameter("user", user).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<SupportChatDialog> findDialogByUserId(Long id) {
        try {
            return Optional.of(entityManager.createQuery("select scd from SupportChatDialog scd where scd.client.id=:id", SupportChatDialog.class).setParameter("id", id).getSingleResult());
        } catch (NonUniqueResultException nure) {
            return Optional.empty();
        }
    }
}

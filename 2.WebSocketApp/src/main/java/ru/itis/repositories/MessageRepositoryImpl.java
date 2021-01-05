package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryImpl implements MessageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Message message) {
        entityManager.persist(message);
    }

    @Override
    @Transactional
    public void delete(Message message) {
        entityManager.remove(entityManager.contains(message) ? message : entityManager.merge(message));
    }

    @Override
    @Transactional
    public void update(Message message) {
        Message updated = entityManager.merge(message);
        message.setAuthor(updated.getAuthor());
        message.setDateTime(updated.getDateTime());
        message.setRoom(updated.getRoom());
        message.setValue(updated.getValue());
    }

    @Override
    @Transactional
    public List<Message> getAll() {
        return entityManager.createQuery("select msg from Message msg", Message.class).getResultList();
    }

    @Override
    @Transactional
    public Optional<Message> find(Long key) {
        return Optional.of(
                entityManager.createQuery("select msg from Message msg where msg.id=:id", Message.class)
                        .setParameter("id", key)
                        .getSingleResult()
        );
    }
}

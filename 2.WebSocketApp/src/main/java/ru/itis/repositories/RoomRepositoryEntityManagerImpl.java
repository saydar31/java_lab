package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class RoomRepositoryEntityManagerImpl implements RoomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Room> findByName(String name) {
        return Optional.of(
                entityManager.createQuery("select room from Room room where room.name=:name", Room.class)
                        .setParameter("name", name)
                        .getSingleResult()
        );
    }

    @Override
    @Transactional
    public void save(Room room) {
        entityManager.persist(room);
    }

    @Override
    @Transactional
    public void delete(Room room) {
        entityManager.remove(entityManager.contains(room) ? room : entityManager.merge(room));
    }

    @Override
    @Transactional
    public void update(Room room) {
        Room updatedRoom = entityManager.merge(room);
        room.setMessages(updatedRoom.getMessages());
        room.setName(updatedRoom.getName());
    }

    @Override
    @Transactional
    public List<Room> getAll() {
        return entityManager.createQuery("select room from Room room", Room.class).getResultList();
    }

    @Override
    @Transactional
    public Optional<Room> find(Long key) {
        return Optional.of(
                entityManager.createQuery("select room from Room room where room.id=:id", Room.class)
                        .setParameter("id", key)
                        .getSingleResult()
        );
    }
}

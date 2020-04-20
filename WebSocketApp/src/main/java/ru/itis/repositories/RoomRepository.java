package ru.itis.repositories;

import ru.itis.model.Room;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {
    Optional<Room> findByName(String name);
}

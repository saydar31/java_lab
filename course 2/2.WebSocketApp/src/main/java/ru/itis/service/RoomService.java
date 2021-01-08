package ru.itis.service;

import ru.itis.dto.RoomListDto;
import ru.itis.model.Room;

public interface RoomService {
    Room getRoomById(Long id);

    RoomListDto getRooms();

    RoomListDto addRoom(String name);
}

package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.RoomDto;
import ru.itis.dto.RoomListDto;
import ru.itis.model.Room;
import ru.itis.repositories.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room getRoomById(Long id) {
        Optional<Room> roomOptional = roomRepository.find(id);
        if (roomOptional.isPresent()) {
            return roomOptional.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public RoomListDto getRooms() {
        return RoomListDto.builder()
                .rooms(roomRepository.getAll().stream().map(RoomDto::from).collect(Collectors.toList()))
                .build();
    }

    @Override
    public RoomListDto addRoom(String name) {
        Room room = Room.builder()
                .name(name)
                .build();
        roomRepository.save(room);
        RoomDto roomDto = RoomDto.builder()
                .name(room.getName())
                .id(room.getId())
                .build();
        return RoomListDto.builder().rooms(List.of(roomDto)).build();
    }
}

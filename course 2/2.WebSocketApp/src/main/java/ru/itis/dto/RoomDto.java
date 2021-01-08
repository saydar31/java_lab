package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.Room;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoomDto {
    private Long id;
    private String name;

    public static RoomDto from(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .name(room.getName())
                .build();
    }
}

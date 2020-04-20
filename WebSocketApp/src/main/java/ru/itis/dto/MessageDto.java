package ru.itis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.Message;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDto {
    private Long id;
    private String value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private UserDto author;

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .value(message.getValue())
                .dateTime(message.getDateTime())
                .author(UserDto.builder().id(message.getAuthor().getId()).userName(message.getAuthor().getUserName()).build())
                .build();
    }
}

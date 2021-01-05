package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.SupportChatMessage;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SupportChatMessageDto {
    private Long id;
    private UserDto userDto;
    private String text;
    private boolean isRead;
    private LocalDateTime dateTime;

    public static SupportChatMessageDto from(SupportChatMessage supportChatMessage) {
        return SupportChatMessageDto.builder()
                .id(supportChatMessage.getId())
                .userDto(UserDto.from(supportChatMessage.getSender()))
                .text(supportChatMessage.getText())
                .isRead(supportChatMessage.isRead())
                .dateTime(supportChatMessage.getDateTime())
                .build();
    }
}

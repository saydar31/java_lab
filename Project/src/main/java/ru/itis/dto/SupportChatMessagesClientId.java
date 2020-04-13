package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.SupportChatMessage;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupportChatMessagesClientId {
    private Long clientId;
    private List<SupportChatMessage> messages;
}

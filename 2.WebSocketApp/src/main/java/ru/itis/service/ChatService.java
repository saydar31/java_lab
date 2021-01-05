package ru.itis.service;

import org.springframework.web.socket.WebSocketSession;
import ru.itis.dto.ClientMessageDto;

public interface ChatService {
    void doService(ClientMessageDto clientMessageDto, WebSocketSession webSocketSession);
    void remove(WebSocketSession session);
}

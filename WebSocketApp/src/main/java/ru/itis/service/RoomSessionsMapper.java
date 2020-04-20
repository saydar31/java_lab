package ru.itis.service;

import org.springframework.web.socket.WebSocketSession;
import ru.itis.model.Room;

import java.util.List;

public interface RoomSessionsMapper {
    List<WebSocketSession> getSessions(Long roomId);

    void putSession(Long roomId, WebSocketSession session);

    void removeSession(WebSocketSession session);
}

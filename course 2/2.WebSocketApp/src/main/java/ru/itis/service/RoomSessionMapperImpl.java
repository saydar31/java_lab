package ru.itis.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class RoomSessionMapperImpl implements RoomSessionsMapper {

    private final Map<Long, List<WebSocketSession>> map = new TreeMap<>();

    @Override
    public List<WebSocketSession> getSessions(Long roomId) {
        return map.get(roomId);
    }

    @Override
    public void putSession(Long roomId, WebSocketSession session) {
        List<WebSocketSession> sessions = map.get(roomId);
        if (sessions == null) {
            sessions = new ArrayList<>();
            sessions.add(session);
            map.put(roomId, sessions);
        } else {
            sessions.add(session);
        }

    }

    @Override
    public void removeSession(WebSocketSession session) {
        map.forEach((key, value) -> value.remove(session));
    }
}

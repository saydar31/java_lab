package ru.itis.javalabmessagequeue.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SubscribeMapperImpl implements SubscribeMapper {
    private final Map<String, WebSocketSession> map;

    public SubscribeMapperImpl() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public WebSocketSession getSession(String queueName) {
        return map.get(queueName);
    }

    @Override
    public void map(String queueName, WebSocketSession session) {
        this.map.put(queueName, session);
    }
}

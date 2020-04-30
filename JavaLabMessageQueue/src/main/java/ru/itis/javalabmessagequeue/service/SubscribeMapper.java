package ru.itis.javalabmessagequeue.service;

import org.springframework.web.socket.WebSocketSession;

public interface SubscribeMapper {
    WebSocketSession getSession(String queueName);

    void map(String queueName, WebSocketSession session);
}

package ru.itis.javalabmessagequeue.service;

import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;

public interface ServiceMapper {
    Optional<Object> getServiceResult(String command, String json, WebSocketSession session);
}

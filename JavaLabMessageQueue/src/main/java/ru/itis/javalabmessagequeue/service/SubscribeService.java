package ru.itis.javalabmessagequeue.service;

import org.springframework.web.socket.WebSocketSession;
import ru.itis.javalabmessagequeue.dto.JlmqReceiveMessage;
import ru.itis.javalabmessagequeue.dto.JlmqSubscribeMessage;

import java.util.List;

public interface SubscribeService {
    List<JlmqReceiveMessage> doService(JlmqSubscribeMessage subscribeMessage, WebSocketSession session);
    List<JlmqReceiveMessage> doService(String queueName);
}

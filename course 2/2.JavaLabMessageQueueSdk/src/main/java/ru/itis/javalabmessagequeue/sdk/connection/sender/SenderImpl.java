package ru.itis.javalabmessagequeue.sdk.connection.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.javalabmessagequeue.sdk.connection.websocket.JlmqWebSocket;
import ru.itis.javalabmessagequeue.sdk.connection.websocket.OnOpenBehavior;

import java.util.Deque;

public class SenderImpl implements Sender {
    private final ObjectMapper objectMapper;
    private final JlmqWebSocket webSocket;
    private final Deque<String> unsentMessages;

    public SenderImpl(ObjectMapper objectMapper, JlmqWebSocket webSocket, Deque<String> deque) {
        this.objectMapper = objectMapper;
        this.webSocket = webSocket;
        this.unsentMessages = deque;
        OnOpenBehavior onOpenBehavior = serverHandshake -> {
            while (!unsentMessages.isEmpty()) {
                webSocket.send(unsentMessages.removeFirst());
            }
        };
        this.webSocket.setOnOpenBehavior(onOpenBehavior);
    }

    @Override
    public void send(Object object) {
        try {
            String messageJson = objectMapper.writeValueAsString(object);
            if (webSocket.isOpen()) {
                webSocket.send(messageJson);
            } else {
                unsentMessages.addLast(messageJson);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

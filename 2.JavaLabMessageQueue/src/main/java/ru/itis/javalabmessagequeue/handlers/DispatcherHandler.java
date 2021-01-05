package ru.itis.javalabmessagequeue.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.javalabmessagequeue.service.ServiceMapper;
import ru.itis.javalabmessagequeue.service.SubscribeMapper;

import java.util.Map;
import java.util.Optional;

@Component
@EnableWebSocket
public class DispatcherHandler extends TextWebSocketHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private SubscribeMapper subscribeMapper;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String json = (String) message.getPayload();
        Map<String, Object> map = objectMapper.readValue(json, Map.class);
        String command = (String) map.get("command");
        Optional<Object> resultOptional = serviceMapper.getServiceResult(command, json, session);
        if (resultOptional.isPresent()) {
            Object result = resultOptional.get();
            String queueName = (String) map.get("queueName");
            if (queueName != null) {
                WebSocketSession socketSession = subscribeMapper.getSession(queueName);
                synchronized (socketSession){
                    socketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(result)));
                }

            }
        }
    }
}

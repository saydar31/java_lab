package ru.itis.handlers.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.dto.ClientMessageDto;
import ru.itis.service.ChatService;

@Component
public class ChatHandler extends TextWebSocketHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chatService.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try {
            String json = (String) message.getPayload();
            ClientMessageDto messageDto = objectMapper.readValue(json, ClientMessageDto.class);
            chatService.doService(messageDto,session);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

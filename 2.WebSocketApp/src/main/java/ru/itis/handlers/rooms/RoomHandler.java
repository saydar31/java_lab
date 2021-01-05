package ru.itis.handlers.rooms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.dto.AddRoomDto;
import ru.itis.dto.RoomListDto;
import ru.itis.service.RoomService;

@Component
@EnableWebSocket
public class RoomHandler extends TextWebSocketHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RoomService roomService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String json = objectMapper.writeValueAsString(roomService.getRooms());
        WebSocketMessage<String> webSocketMessage = new TextMessage(json);
        session.sendMessage(webSocketMessage);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String json = (String) message.getPayload();
        AddRoomDto addRoomDto = objectMapper.readValue(json, AddRoomDto.class);
        RoomListDto added = roomService.addRoom(addRoomDto.getRoomName());
        WebSocketMessage<String> webSocketMessage = new TextMessage(objectMapper.writeValueAsString(added));
        session.sendMessage(webSocketMessage);
    }
}

package ru.itis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.itis.dto.ClientMessageDto;
import ru.itis.dto.MessageDto;
import ru.itis.dto.MessageListDto;
import ru.itis.model.Message;
import ru.itis.model.Room;
import ru.itis.model.User;
import ru.itis.repositories.MessageRepository;
import ru.itis.repositories.RoomRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ChatServiceImpl implements ChatService {
    @Autowired
    private RoomSessionsMapper roomSessionsMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MessageRepository messageRepository;

    @SneakyThrows
    @Override
    public void doService(ClientMessageDto clientMessageDto, WebSocketSession webSocketSession) {
        if (clientMessageDto.isInit()) {
            roomSessionsMapper.putSession(clientMessageDto.getChatId(), webSocketSession);
        } else {
            String token = clientMessageDto.getAuthentication();
            User user = authenticationService.getUserByToken(token);
            Optional<Room> roomOptional = roomRepository.find(clientMessageDto.getChatId());
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                Message message = Message.builder()
                        .author(user)
                        .dateTime(LocalDateTime.now())
                        .room(room)
                        .value(clientMessageDto.getMessage())
                        .build();
                messageRepository.save(message);
                room.getMessages().add(message);
                roomRepository.update(room);
                MessageDto messageDto = MessageDto.from(message);
                MessageListDto messageListDto = MessageListDto.builder()
                        .messages(List.of(messageDto))
                        .build();
                String json = objectMapper.writeValueAsString(messageListDto);
                WebSocketMessage<String> webSocketMessage = new TextMessage(json);
                roomSessionsMapper.getSessions(room.getId()).forEach(session -> {
                    try {
                        session.sendMessage(webSocketMessage);
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                });
            }
        }
    }

    @Override
    public void remove(WebSocketSession session) {
        roomSessionsMapper.removeSession(session);
    }
}

package ru.itis.handlers.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.dto.AuthTokenDto;
import ru.itis.dto.SignInDto;
import ru.itis.service.AuthenticationService;

@EnableWebSocket
@Component
public class SignInHandler extends TextWebSocketHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    AuthenticationService authenticationService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String json = (String) message.getPayload();
        SignInDto signInDto = objectMapper.readValue(json, SignInDto.class);
        String token = authenticationService.signIn(signInDto);
        AuthTokenDto authTokenDto = AuthTokenDto.builder()
                .success(true)
                .token(token)
                .build();
        String messageJson = objectMapper.writeValueAsString(authTokenDto);
        WebSocketMessage<String> webSocketMessage = new TextMessage(messageJson);
        session.sendMessage(webSocketMessage);
    }
}

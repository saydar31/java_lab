package ru.itis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.itis.handlers.GlobalAuthHandShakeHandler;
import ru.itis.handlers.chat.ChatHandler;
import ru.itis.handlers.login.SignInHandler;
import ru.itis.handlers.rooms.RoomHandler;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private GlobalAuthHandShakeHandler authHandShakeHandler;
    @Autowired
    private SignInHandler signInHandler;
    @Autowired
    private ChatHandler chatHandler;
    @Autowired
    private RoomHandler roomHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(signInHandler, "/signIn").setAllowedOrigins("*").withSockJS();
        webSocketHandlerRegistry.addHandler(chatHandler, "/chat").setHandshakeHandler(authHandShakeHandler).setAllowedOrigins("*").withSockJS();
        webSocketHandlerRegistry.addHandler(roomHandler, "/rooms").setHandshakeHandler(authHandShakeHandler).setAllowedOrigins("*").withSockJS();
    }
}

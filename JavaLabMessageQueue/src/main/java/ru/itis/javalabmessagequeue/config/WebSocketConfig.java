package ru.itis.javalabmessagequeue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.itis.javalabmessagequeue.handlers.DispatcherHandler;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private DispatcherHandler dispatcherHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(dispatcherHandler,"/endpoint").setAllowedOrigins("*");
    }
}

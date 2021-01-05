package ru.itis.javalabmessagequeue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.itis.javalabmessagequeue.handlers.DispatcherHandler;
import ru.itis.javalabmessagequeue.interceptors.AuthInterceptor;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private DispatcherHandler dispatcherHandler;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(dispatcherHandler, "/endpoint").addInterceptors(authInterceptor).setAllowedOrigins("*");
    }
}

package ru.itis.javalabmessagequeue.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ru.itis.javalabmessagequeue.service.AuthenticationService;

import java.util.List;
import java.util.Map;

@Component
public class AuthInterceptor implements HandshakeInterceptor {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        List<String> values = serverHttpRequest.getHeaders().get("Authorization");
        if (values != null && values.size() == 1) {
            String key = values.get(0);
            boolean authResult = authenticationService.authenticate(key);
            if (!authResult) {
                serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
            }
            return authResult;
        }
        serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}

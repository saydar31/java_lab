package ru.itis.javalabmessagequeue.sdk.stomp.connection.stomp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import ru.itis.javalabmessagequeue.sdk.stomp.consumer.ConsumerMapper;
import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqReceiveMessage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@AllArgsConstructor
public class SessionHandlerImpl implements StompSessionHandler {
    private final ConsumerMapper consumerMapper;
    private ObjectMapper objectMapper;
    private TypeReference<List<JlmqReceiveMessage>> typeReference;

    public SessionHandlerImpl(ConsumerMapper consumerMapper,ObjectMapper objectMapper) {
        this.consumerMapper = consumerMapper;
        this.objectMapper = objectMapper;
        this.typeReference = new TypeReference<List<JlmqReceiveMessage>>() {
        };
    }

    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        System.out.println("connected");
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        System.err.println("handle exception");
        throwable.printStackTrace();
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        System.err.println("transport error");
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        String json = (String) o;
        try {
            List<JlmqReceiveMessage> receiveMessages = objectMapper.readValue(json, typeReference);
            receiveMessages.forEach(jlmqReceiveMessage -> consumerMapper.getConsumer(jlmqReceiveMessage.getQueueName()).handle(jlmqReceiveMessage));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

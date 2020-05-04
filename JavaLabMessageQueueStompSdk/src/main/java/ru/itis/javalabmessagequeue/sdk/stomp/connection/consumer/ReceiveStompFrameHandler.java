package ru.itis.javalabmessagequeue.sdk.stomp.connection.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import ru.itis.javalabmessagequeue.sdk.stomp.consumer.ConsumerMapper;
import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqReceiveMessage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@AllArgsConstructor
public class ReceiveStompFrameHandler implements StompFrameHandler {
    private final ConsumerMapper consumerMapper;
    private final TypeReference<List<JlmqReceiveMessage>> listTypeReference;
    private final ObjectMapper objectMapper;

    public ReceiveStompFrameHandler(ConsumerMapper consumerMapper,ObjectMapper objectMapper) {
        this.consumerMapper = consumerMapper;
        this.objectMapper = new ObjectMapper();
        this.listTypeReference = new TypeReference<List<JlmqReceiveMessage>>() {
        };
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
      return   byte[].class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        System.out.println("handle");
        byte[] json = (byte[]) o;
        try {
            List<JlmqReceiveMessage> receiveMessages = objectMapper.readValue(json, listTypeReference);
            receiveMessages.forEach(jlmqReceiveMessage -> consumerMapper.getConsumer(jlmqReceiveMessage.getQueueName()).handle(jlmqReceiveMessage));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

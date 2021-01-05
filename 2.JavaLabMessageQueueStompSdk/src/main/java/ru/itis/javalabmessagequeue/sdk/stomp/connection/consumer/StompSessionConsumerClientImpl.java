package ru.itis.javalabmessagequeue.sdk.stomp.connection.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompSession;
import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqAcceptMessage;
import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqCompleteMessage;

@AllArgsConstructor
public class StompSessionConsumerClientImpl implements ConsumerClient {
    private final StompSession stompSession;
    private final StompFrameHandler receiveStompFrameHandler;
    private final ObjectMapper objectMapper;

    @Override
    public void subscribe(String queueName) {
        stompSession.subscribe("/topic/queue/" + queueName, receiveStompFrameHandler);
    }

    @Override
    public void accept(String messageId) {
        JlmqAcceptMessage acceptMessage = JlmqAcceptMessage.builder()
                .messageId(messageId)
                .build();
        byte[] data = toByteArray(acceptMessage);
        stompSession.send("/app/accept", data);
    }

    @Override
    public void complete(String messageId) {
        JlmqCompleteMessage completeMessage = JlmqCompleteMessage.builder()
                .messageId(messageId)
                .build();
        byte[] data = toByteArray(completeMessage);
        stompSession.send("/app/complete", data);
    }

    private byte[] toByteArray(Object o){
        try {
            return objectMapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

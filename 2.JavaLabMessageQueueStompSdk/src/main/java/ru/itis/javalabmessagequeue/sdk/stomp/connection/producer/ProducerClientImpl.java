package ru.itis.javalabmessagequeue.sdk.stomp.connection.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;
import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqStompSendMessage;

@AllArgsConstructor
public class ProducerClientImpl implements ProducerClient {
    private final StompSession stompSession;
    private final ObjectMapper objectMapper;

    public ProducerClientImpl(StompSession stompSession) {
        this.stompSession = stompSession;
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public void send(String queueName, Object body) {
        JlmqStompSendMessage sendMessage = JlmqStompSendMessage.builder()
                .queueName(queueName)
                .body(body)
                .build();

        try {
            byte[] data = objectMapper.writeValueAsBytes(sendMessage);
            stompSession.send("/app/send", data);
            System.out.println(new String(data));
            System.out.println("ccddcd");
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

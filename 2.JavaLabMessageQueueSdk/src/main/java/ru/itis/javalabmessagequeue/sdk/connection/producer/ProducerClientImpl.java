package ru.itis.javalabmessagequeue.sdk.connection.producer;

import lombok.AllArgsConstructor;
import ru.itis.javalabmessagequeue.sdk.connection.sender.SenderPool;
import ru.itis.javalabmessagequeue.sdk.protocol.JlmqSendMessage;
@AllArgsConstructor
public class ProducerClientImpl implements ProducerClient {
    private final SenderPool senderPool;

    @Override
    public void send(String queueName, Object body) {
        JlmqSendMessage sendMessage = JlmqSendMessage.builder()
                .queueName(queueName)
                .body(body)
                .build();
        senderPool.getSender().send(sendMessage);
    }
}

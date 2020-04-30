package ru.itis.javalabmessagequeue.sdk.connection.consumer;

import lombok.AllArgsConstructor;
import ru.itis.javalabmessagequeue.sdk.connection.sender.SenderPool;
import ru.itis.javalabmessagequeue.sdk.protocol.JlmqAcceptMessage;
import ru.itis.javalabmessagequeue.sdk.protocol.JlmqCompleteMessage;
import ru.itis.javalabmessagequeue.sdk.protocol.JlmqSubscribeMessage;

@AllArgsConstructor
public class ConsumerClientImpl implements ConsumerClient {
    private final SenderPool senderPool;

    @Override
    public void subscribe(String queueName) {
        JlmqSubscribeMessage subscribeMessage = JlmqSubscribeMessage.builder()
                .queueName(queueName)
                .build();
        senderPool.getSender().send(subscribeMessage);
    }

    @Override
    public void accept(String messageId) {
        JlmqAcceptMessage acceptMessage = JlmqAcceptMessage.builder()
                .messageId(messageId)
                .build();
        senderPool.getSender().send(acceptMessage);
    }

    @Override
    public void complete(String messageId) {
        JlmqCompleteMessage completeMessage = JlmqCompleteMessage.builder()
                .messageId(messageId)
                .build();
        senderPool.getSender().send(completeMessage);
    }
}

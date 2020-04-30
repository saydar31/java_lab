package ru.itis.javalabmessagequeue.sdk.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.javalabmessagequeue.sdk.connection.consumer.ConsumerClient;
import ru.itis.javalabmessagequeue.sdk.protocol.JlmqReceiveMessage;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ConsumerImpl implements Consumer {
    private ConsumerClient consumerClient;
    private ReceiveHandler receiveHandler;
    private String queueName;

    @Override
    public void handle(JlmqReceiveMessage receiveMessage) {
        consumerClient.accept(receiveMessage.getMessageId());
        receiveHandler.handle(receiveMessage);
        consumerClient.complete(receiveMessage.getMessageId());
    }
}

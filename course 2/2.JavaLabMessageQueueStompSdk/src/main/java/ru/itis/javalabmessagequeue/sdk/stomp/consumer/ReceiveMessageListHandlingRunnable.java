package ru.itis.javalabmessagequeue.sdk.stomp.consumer;

import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqReceiveMessage;

import java.util.List;

public class ReceiveMessageListHandlingRunnable implements Runnable {
    private final List<JlmqReceiveMessage> receiveMessages;
    private final ConsumerMapper consumerMapper;

    public ReceiveMessageListHandlingRunnable(List<JlmqReceiveMessage> receiveMessages, ConsumerMapper consumerMapper) {
        this.receiveMessages = receiveMessages;
        this.consumerMapper = consumerMapper;
    }

    @Override
    public void run() {
        for (JlmqReceiveMessage receiveMessage :
                receiveMessages) {
            consumerMapper.getConsumer(receiveMessage.getQueueName()).handle(receiveMessage);
        }
    }
}

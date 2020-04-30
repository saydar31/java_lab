package ru.itis.javalabmessagequeue.sdk.connection.consumer;

public interface ConsumerClient {
    void subscribe(String queueName);

    void accept(String messageId);

    void complete(String messageId);
}

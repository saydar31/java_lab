package ru.itis.javalabmessagequeue.sdk.stomp.connection.producer;

public interface ProducerClient {
    void send(String queueName, Object body);
}

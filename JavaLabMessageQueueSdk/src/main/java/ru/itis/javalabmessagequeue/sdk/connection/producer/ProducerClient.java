package ru.itis.javalabmessagequeue.sdk.connection.producer;

public interface ProducerClient {
    void send(String queueName, Object body);
}

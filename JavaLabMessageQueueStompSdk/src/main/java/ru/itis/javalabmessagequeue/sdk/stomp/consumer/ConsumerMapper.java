package ru.itis.javalabmessagequeue.sdk.stomp.consumer;

public interface ConsumerMapper {
    void put(String queueName, Consumer consumer);

    Consumer getConsumer(String queueName);
}

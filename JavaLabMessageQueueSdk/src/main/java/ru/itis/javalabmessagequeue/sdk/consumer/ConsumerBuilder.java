package ru.itis.javalabmessagequeue.sdk.consumer;

import ru.itis.javalabmessagequeue.sdk.connection.consumer.ConsumerClient;

public class ConsumerBuilder {
    private final ConsumerImpl consumer;
    private final ConsumerMapper consumerMapper;
    private final ConsumerClient consumerClient;

    public ConsumerBuilder(ConsumerMapper consumerMapper, ConsumerClient consumerClient) {
        this.consumerMapper = consumerMapper;
        this.consumerClient = consumerClient;
        this.consumer = new ConsumerImpl();
    }

    public ConsumerBuilder forQueue(String queueName) {
        consumerMapper.put(queueName, consumer);
        consumer.setQueueName(queueName);
        return this;
    }

    public ConsumerBuilder onReceive(ReceiveHandler receiveHandler) {
        consumer.setReceiveHandler(receiveHandler);
        return this;
    }

    public Consumer create() {
        consumer.setConsumerClient(consumerClient);
        consumerClient.subscribe(consumer.getQueueName());
        return consumer;
    }
}

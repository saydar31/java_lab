package ru.itis.javalabmessagequeue.sdk.consumer;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SimpleConsumerMapperImpl implements ConsumerMapper {
    private final Map<String, Consumer> consumerMap;

    public SimpleConsumerMapperImpl() {
        this.consumerMap = new HashMap<>();
    }

    @Override
    public void put(String queueName, Consumer consumer) {
        consumerMap.put(queueName, consumer);
    }

    @Override
    public Consumer getConsumer(String queueName) {
        return consumerMap.get(queueName);
    }
}

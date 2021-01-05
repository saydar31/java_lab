package ru.itis.javalabmessagequeue.sdk.connector;

import ru.itis.javalabmessagequeue.sdk.consumer.ConsumerBuilder;
import ru.itis.javalabmessagequeue.sdk.producer.ProducerBuilder;

public interface Connector {
    ConsumerBuilder consumer();

    ProducerBuilder producer();
}

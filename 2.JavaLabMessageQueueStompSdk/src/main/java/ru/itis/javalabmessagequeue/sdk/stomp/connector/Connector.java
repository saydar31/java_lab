package ru.itis.javalabmessagequeue.sdk.stomp.connector;

import ru.itis.javalabmessagequeue.sdk.stomp.consumer.ConsumerBuilder;
import ru.itis.javalabmessagequeue.sdk.stomp.producer.ProducerBuilder;

public interface Connector {
    ConsumerBuilder consumer();

    ProducerBuilder producer();
}

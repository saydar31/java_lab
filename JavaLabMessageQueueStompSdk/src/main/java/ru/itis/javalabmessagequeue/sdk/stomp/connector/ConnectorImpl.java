package ru.itis.javalabmessagequeue.sdk.stomp.connector;

import lombok.AllArgsConstructor;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.consumer.ConsumerClient;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.producer.ProducerClient;
import ru.itis.javalabmessagequeue.sdk.stomp.consumer.ConsumerBuilder;
import ru.itis.javalabmessagequeue.sdk.stomp.consumer.ConsumerMapper;
import ru.itis.javalabmessagequeue.sdk.stomp.producer.ProducerBuilder;
@AllArgsConstructor
public class ConnectorImpl implements Connector{
    private final ConsumerClient consumerClient;
    private final ConsumerMapper consumerMapper;
    private final ProducerClient producerClient;

    @Override
    public ConsumerBuilder consumer() {
        return new ConsumerBuilder(consumerMapper,consumerClient);
    }

    @Override
    public ProducerBuilder producer() {
        return new ProducerBuilder(producerClient);
    }
}

package ru.itis.javalabmessagequeue.sdk.stomp.producer;

import ru.itis.javalabmessagequeue.sdk.stomp.connection.producer.ProducerClient;

public class ProducerBuilder {
    private final ProducerClient producerClient;
    private final ProducerImpl producer;

    public ProducerBuilder(ProducerClient producerClient) {
        this.producerClient = producerClient;
        this.producer=new ProducerImpl();
    }

    public ProducerBuilder forQueue(String queueName) {
        producer.setQueueName(queueName);
        return this;
    }

    public Producer create() {
        producer.setProducerClient(producerClient);
        return producer;
    }
}

package ru.itis.javalabmessagequeue.sdk.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itis.javalabmessagequeue.sdk.connection.producer.ProducerClient;

@AllArgsConstructor
@NoArgsConstructor
public class ProducerImpl implements Producer {
    @Setter
    @Getter
    private String queueName;
    @Setter
    private ProducerClient producerClient;

    @Override
    public void send(Object body) {
        producerClient.send(queueName, body);
    }
}

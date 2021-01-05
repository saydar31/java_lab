package ru.itis.contractgenerator.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class Receiver {
    private final Channel channel;

    private final DeliverCallback deliverCallback;

    private static final String EXCHANGE_NAME = "contract_exchange";

    private static final String EXCHANGE_TYPE = "fanout";

    public Receiver(Channel channel, DeliverCallback deliverCallback) {
        this.channel = channel;
        this.deliverCallback = deliverCallback;

    }

    public void startReceiving() {
        try {
            channel.basicQos(3);
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");
            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException e){
            throw new IllegalStateException(e);
        }
    }
}
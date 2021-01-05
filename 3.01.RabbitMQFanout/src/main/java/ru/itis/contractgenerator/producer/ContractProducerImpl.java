package ru.itis.contractgenerator.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.contractgenerator.model.PersonInfo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class ContractProducerImpl implements ContractProducer {

    private final ObjectMapper objectMapper;

    private final Channel channel;

    private static final String EXCHANGE_NAME ="contract_exchange";

    private static final String EXCHANGE_TYPE="fanout";

    public ContractProducerImpl(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        this.objectMapper=objectMapper;
        try {
            this.channel = connectionFactory.newConnection().createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        } catch (TimeoutException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void produceContract(PersonInfo personInfo) {
        try {
            channel.basicPublish(EXCHANGE_NAME,"",null, objectMapper.writeValueAsBytes(personInfo));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}

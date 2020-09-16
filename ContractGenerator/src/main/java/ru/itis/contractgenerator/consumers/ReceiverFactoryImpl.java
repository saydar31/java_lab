package ru.itis.contractgenerator.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.contractgenerator.services.PdfService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class ReceiverFactoryImpl implements ReceiverFactory {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private PdfService pdfService;

    @Override
    public Receiver getReceiver(String header) {
        try {
            Channel channel = connectionFactory.newConnection().createChannel();
            DeliverCallback deliverCallback = new ContractDeliverCallback(header, channel, objectMapper, pdfService);
            return new Receiver(channel, deliverCallback);
        } catch (IOException | TimeoutException e) {
            throw new IllegalStateException(e);
        }
    }
}

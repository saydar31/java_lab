package ru.itis.contractgenerator.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.AllArgsConstructor;
import ru.itis.contractgenerator.model.Contract;
import ru.itis.contractgenerator.model.PersonInfo;
import ru.itis.contractgenerator.services.PdfService;

import java.io.IOException;
@AllArgsConstructor
public class ContractDeliverCallback implements DeliverCallback {

    private final String header;

    private final Channel channel;

    private final ObjectMapper objectMapper;

    private final PdfService pdfService;

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        PersonInfo personInfo = objectMapper.readValue(delivery.getBody(), PersonInfo.class);
        pdfService.createContract(new Contract(header, personInfo));
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }
}

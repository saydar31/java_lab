package ru.itis.contractgenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.contractgenerator.consumers.Receiver;
import ru.itis.contractgenerator.consumers.ReceiverFactory;

@Component
public class ReceiverController {
    @Autowired
    private ReceiverFactory receiverFactory;

    public void initializeReceivers(String headers) {
        String[] headersArray = headers.split(";");
        for (String header :
                headersArray) {
            Receiver receiver = receiverFactory.getReceiver(header);
            receiver.startReceiving();
        }
    }
}

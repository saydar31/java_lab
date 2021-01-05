package ru.itis.javalabmessagequeue.sdk.connection.receiver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.javalabmessagequeue.sdk.consumer.ReceiveMessageListHandler;
import ru.itis.javalabmessagequeue.sdk.protocol.JlmqReceiveMessage;

import java.io.IOException;
import java.util.List;

public class ReceiverImpl implements Receiver {
    private final ObjectMapper objectMapper;
    private final TypeReference<List<JlmqReceiveMessage>> typeReference;
    private final ReceiveMessageListHandler receiveMessageListHandler;

    public ReceiverImpl(ObjectMapper objectMapper, ReceiveMessageListHandler receiveMessageListHandler) {
        this.objectMapper = objectMapper;
        this.receiveMessageListHandler = receiveMessageListHandler;
        typeReference = new TypeReference<List<JlmqReceiveMessage>>() {};
    }

    @Override
    public void receive(String message) {
        try {
            List<JlmqReceiveMessage> receiveMessages = objectMapper.readValue(message, typeReference);
            receiveMessageListHandler.handleList(receiveMessages);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

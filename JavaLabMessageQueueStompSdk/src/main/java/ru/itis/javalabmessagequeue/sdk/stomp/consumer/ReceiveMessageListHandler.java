package ru.itis.javalabmessagequeue.sdk.stomp.consumer;

import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqReceiveMessage;

import java.util.List;

public interface ReceiveMessageListHandler {
    void handleList(List<JlmqReceiveMessage> receiveMessages);
}

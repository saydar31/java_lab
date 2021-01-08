package ru.itis.javalabmessagequeue.sdk.consumer;

import ru.itis.javalabmessagequeue.sdk.protocol.JlmqReceiveMessage;

import java.util.List;

public interface ReceiveMessageListHandler {
    void handleList(List<JlmqReceiveMessage> receiveMessages);
}

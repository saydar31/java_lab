package ru.itis.javalabmessagequeue.sdk.consumer;

import ru.itis.javalabmessagequeue.sdk.protocol.JlmqReceiveMessage;

public interface ReceiveHandler {
    void handle(JlmqReceiveMessage receiveMessage);
}

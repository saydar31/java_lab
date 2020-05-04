package ru.itis.javalabmessagequeue.sdk.stomp.consumer;

import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqReceiveMessage;

public interface ReceiveHandler {
    void handle(JlmqReceiveMessage receiveMessage);
}

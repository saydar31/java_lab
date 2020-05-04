package ru.itis.javalabmessagequeue.sdk.stomp.consumer;

import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqReceiveMessage;

public interface Consumer {

    void handle(JlmqReceiveMessage receiveMessage);
}

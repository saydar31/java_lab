package ru.itis.javalabmessagequeue.sdk.consumer;

import ru.itis.javalabmessagequeue.sdk.protocol.JlmqReceiveMessage;

public interface Consumer {

    void handle(JlmqReceiveMessage receiveMessage);
}

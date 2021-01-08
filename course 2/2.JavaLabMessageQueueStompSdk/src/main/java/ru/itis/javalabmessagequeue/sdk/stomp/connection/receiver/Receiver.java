package ru.itis.javalabmessagequeue.sdk.stomp.connection.receiver;

public interface Receiver {
    void receive(String message);
}

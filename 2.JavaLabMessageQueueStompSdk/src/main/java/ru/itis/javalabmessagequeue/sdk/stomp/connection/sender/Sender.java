package ru.itis.javalabmessagequeue.sdk.stomp.connection.sender;

public interface Sender {
    void send(Object object);
    void send(String destination,Object body);
}

package ru.itis.javalabmessagequeue.sdk.stomp.connection.sender;

import org.springframework.messaging.simp.stomp.StompSession;

public class SenderImpl implements Sender{
    private StompSession stompSession;

    @Override
    public void send(Object object) {

    }

    @Override
    public void send(String destination, Object body) {

    }
}

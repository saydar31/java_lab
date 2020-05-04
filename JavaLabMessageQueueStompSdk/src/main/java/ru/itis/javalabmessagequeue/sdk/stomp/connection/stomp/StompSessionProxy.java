package ru.itis.javalabmessagequeue.sdk.stomp.connection.stomp;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

public class StompSessionProxy implements StompSession {
    private StompSession stompSession;

    @Override
    public String getSessionId() {
        return stompSession.getSessionId();
    }

    @Override
    public boolean isConnected() {
        return stompSession.isConnected();
    }

    @Override
    public void setAutoReceipt(boolean b) {
        stompSession.setAutoReceipt(b);
    }

    @Override
    public Receiptable send(String s, Object o) {
        return stompSession.send(s, o);
    }

    @Override
    public Receiptable send(StompHeaders stompHeaders, Object o) {
        return stompSession.send(stompHeaders, o);
    }

    @Override
    public Subscription subscribe(String s, StompFrameHandler stompFrameHandler) {
        return stompSession.subscribe(s, stompFrameHandler);
    }

    @Override
    public Subscription subscribe(StompHeaders stompHeaders, StompFrameHandler stompFrameHandler) {
        return stompSession.subscribe(stompHeaders, stompFrameHandler);
    }

    @Override
    public Receiptable acknowledge(String s, boolean b) {
        return stompSession.acknowledge(s, b);
    }

    @Override
    public Receiptable acknowledge(StompHeaders stompHeaders, boolean b) {
        return stompSession.acknowledge(stompHeaders, b);
    }

    @Override
    public void disconnect() {
        stompSession.disconnect();
    }

    @Override
    public void disconnect(StompHeaders stompHeaders) {
        stompSession.disconnect(stompHeaders);
    }
}

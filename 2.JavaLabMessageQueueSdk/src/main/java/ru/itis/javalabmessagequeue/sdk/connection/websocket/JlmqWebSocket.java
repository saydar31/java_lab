package ru.itis.javalabmessagequeue.sdk.connection.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import ru.itis.javalabmessagequeue.sdk.connection.receiver.Receiver;

import java.net.URI;
import java.util.Map;

public class JlmqWebSocket extends WebSocketClient {
    private final Receiver receiver;
    private OnOpenBehavior onOpenBehavior;

    public JlmqWebSocket(URI serverUri, Receiver receiver) {
        super(serverUri);
        this.receiver = receiver;
    }

    public JlmqWebSocket(URI serverUri, Map<String, String> httpHeaders, Receiver receiver) {
        super(serverUri, httpHeaders);
        this.receiver = receiver;
    }

    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("open");
        onOpenBehavior.open(serverHandshake);
    }

    public void onMessage(String s) {
        receiver.receive(s);
    }

    public void onClose(int i, String s, boolean b) {

    }

    public void onError(Exception e) {

    }

    public void setOnOpenBehavior(OnOpenBehavior onOpenBehavior) {
        this.onOpenBehavior = onOpenBehavior;
    }
}

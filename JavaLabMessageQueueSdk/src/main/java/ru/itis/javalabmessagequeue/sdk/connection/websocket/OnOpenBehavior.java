package ru.itis.javalabmessagequeue.sdk.connection.websocket;

import org.java_websocket.handshake.ServerHandshake;

public interface OnOpenBehavior {
    void open(ServerHandshake serverHandshake);
}

package ru.itis.javalabmessagequeue.sdk.stomp.connection.sender;

import lombok.AllArgsConstructor;

import java.util.Deque;

@AllArgsConstructor
public class SenderPoolImpl implements SenderPool {
    private final Deque<Sender> senders;

    @Override
    public Sender getSender() {
        synchronized (senders) {
            Sender sender = senders.removeFirst();
            senders.addLast(sender);
            return sender;
        }
    }
}

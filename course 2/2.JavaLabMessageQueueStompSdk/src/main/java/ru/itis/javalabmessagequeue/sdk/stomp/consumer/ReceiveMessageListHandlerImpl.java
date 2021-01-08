package ru.itis.javalabmessagequeue.sdk.stomp.consumer;

import lombok.AllArgsConstructor;
import ru.itis.javalabmessagequeue.sdk.stomp.protocol.JlmqReceiveMessage;

import java.util.List;
import java.util.concurrent.ExecutorService;

@AllArgsConstructor
public class ReceiveMessageListHandlerImpl implements ReceiveMessageListHandler {
    private final ConsumerMapper consumerMapper;
    private final ExecutorService executorService;

    @Override
    public void handleList(List<JlmqReceiveMessage> receiveMessages) {
        ReceiveMessageListHandlingRunnable handlingRunnable = new ReceiveMessageListHandlingRunnable(receiveMessages, consumerMapper);
        executorService.submit(handlingRunnable);
    }
}

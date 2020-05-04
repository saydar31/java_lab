package ru.itis.javalabmessagequeue.sdk.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ru.itis.javalabmessagequeue.sdk.connection.consumer.ConsumerClient;
import ru.itis.javalabmessagequeue.sdk.connection.consumer.ConsumerClientImpl;
import ru.itis.javalabmessagequeue.sdk.connection.producer.ProducerClient;
import ru.itis.javalabmessagequeue.sdk.connection.producer.ProducerClientImpl;
import ru.itis.javalabmessagequeue.sdk.connection.receiver.Receiver;
import ru.itis.javalabmessagequeue.sdk.connection.receiver.ReceiverImpl;
import ru.itis.javalabmessagequeue.sdk.connection.sender.Sender;
import ru.itis.javalabmessagequeue.sdk.connection.sender.SenderImpl;
import ru.itis.javalabmessagequeue.sdk.connection.sender.SenderPool;
import ru.itis.javalabmessagequeue.sdk.connection.sender.SenderPoolImpl;
import ru.itis.javalabmessagequeue.sdk.connection.websocket.JlmqWebSocket;
import ru.itis.javalabmessagequeue.sdk.connector.Connector;
import ru.itis.javalabmessagequeue.sdk.connector.ConnectorImpl;
import ru.itis.javalabmessagequeue.sdk.consumer.ConsumerMapper;
import ru.itis.javalabmessagequeue.sdk.consumer.ReceiveMessageListHandler;
import ru.itis.javalabmessagequeue.sdk.consumer.ReceiveMessageListHandlerImpl;
import ru.itis.javalabmessagequeue.sdk.consumer.SimpleConsumerMapperImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;

public class JlmqConfigurer {

    public Connector configureConnector(JlmqConfiguration configuration) throws URISyntaxException {
        String url = configuration.getUrl();
        URI uri = new URI(url);
        int connectionCount = configuration.getConnectionsCount() != null ? configuration.getConnectionsCount() : 3;
        Deque<Sender> senderDeque = new ConcurrentLinkedDeque<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ConsumerMapper consumerMapper = new SimpleConsumerMapperImpl();
        ReceiveMessageListHandler listHandler = new ReceiveMessageListHandlerImpl(consumerMapper, Executors.newSingleThreadExecutor());
        Receiver receiver = new ReceiverImpl(objectMapper, listHandler);
        Map<String, String> headers = new HashMap<>();
        if (configuration.getAuthenticationKey() != null) {
            headers.put("Authorization", configuration.getAuthenticationKey());
        }
        for (int i = 0; i < connectionCount; i++) {
            JlmqWebSocket webSocket;
            if (configuration.getAuthenticationKey() != null) {
                webSocket = new JlmqWebSocket(uri, headers, receiver);
            } else {
                webSocket = new JlmqWebSocket(uri, receiver);
            }
            Deque<String> stringDeque = new LinkedList<>();
            senderDeque.addLast(new SenderImpl(objectMapper, webSocket, stringDeque));
            webSocket.connect();
        }
        SenderPool senderPool = new SenderPoolImpl(senderDeque);
        ProducerClient producerClient = new ProducerClientImpl(senderPool);
        ConsumerClient consumerClient = new ConsumerClientImpl(senderPool);
        return new ConnectorImpl(consumerClient, consumerMapper, producerClient);
    }
}

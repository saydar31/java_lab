package ru.itis.javalabmessagequeue.sdk.stomp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.consumer.ConsumerClient;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.consumer.ReceiveStompFrameHandler;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.consumer.StompSessionConsumerClientImpl;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.producer.ProducerClient;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.producer.ProducerClientImpl;
import ru.itis.javalabmessagequeue.sdk.stomp.connection.stomp.SessionHandlerImpl;
import ru.itis.javalabmessagequeue.sdk.stomp.connector.Connector;
import ru.itis.javalabmessagequeue.sdk.stomp.connector.ConnectorImpl;
import ru.itis.javalabmessagequeue.sdk.stomp.consumer.ConsumerMapper;
import ru.itis.javalabmessagequeue.sdk.stomp.consumer.SimpleConsumerMapperImpl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JlmqConfigurer {

    public Connector configureConnector(JlmqConfiguration configuration) throws InterruptedException, ExecutionException, TimeoutException {
        ObjectMapper objectMapper = new ObjectMapper();
        ConsumerMapper consumerMapper = new SimpleConsumerMapperImpl();
        StompFrameHandler stompFrameHandler = new ReceiveStompFrameHandler(consumerMapper, objectMapper);
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        StompSessionHandler stompSessionHandler = new SessionHandlerImpl(consumerMapper, objectMapper);
        StompSession stompSession = stompClient.connect(configuration.getUrl(), stompSessionHandler).get(100, TimeUnit.SECONDS);
        ConsumerClient consumerClient = new StompSessionConsumerClientImpl(stompSession, stompFrameHandler, objectMapper);
        ProducerClient producerClient = new ProducerClientImpl(stompSession);
        return new ConnectorImpl(consumerClient, consumerMapper, producerClient);
    }
}

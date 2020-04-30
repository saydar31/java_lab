package ru.itis.javalabmessagequeue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import ru.itis.javalabmessagequeue.dto.JlmqReceiveMessage;
import ru.itis.javalabmessagequeue.dto.JlmqSubscribeMessage;
import ru.itis.javalabmessagequeue.model.Message;
import ru.itis.javalabmessagequeue.repositories.MessageRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SubscribeServiceImpl implements SubscribeService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SubscribeMapper subscribeMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public List<JlmqReceiveMessage> doService(JlmqSubscribeMessage subscribeMessage, WebSocketSession session) {
        subscribeMapper.map(subscribeMessage.getQueueName(), session);
        List<Message> messages = messageRepository.findByCompletedFalseAndQueueName(subscribeMessage.getQueueName());
        Function<Message, JlmqReceiveMessage> function = message ->
        {
            try {
                return JlmqReceiveMessage.builder()
                        .body(objectMapper.readValue(message.getBody(), Object.class))
                        .messageId(message.getId())
                        .queueName(message.getQueue().getName())
                        .build();
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
        };
        return messages.stream().map(function).collect(Collectors.toList());
    }
}

package ru.itis.javalabmessagequeue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalabmessagequeue.dto.JlmqReceiveMessage;
import ru.itis.javalabmessagequeue.dto.JlmqSendMessage;
import ru.itis.javalabmessagequeue.model.Message;
import ru.itis.javalabmessagequeue.model.MessageQueue;
import ru.itis.javalabmessagequeue.repositories.MessageQueueRepository;
import ru.itis.javalabmessagequeue.repositories.MessageRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class SendServiceImpl implements SendService {
    @Autowired
    private MessageRepository repository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageQueueRepository messageQueueRepository;

    @Override
    public List<JlmqReceiveMessage> doService(JlmqSendMessage jlmqSendMessage) {
        try {
            Optional<MessageQueue> messageQueueOptional = messageQueueRepository.findById(jlmqSendMessage.getQueueName());
            if (messageQueueOptional.isPresent()) {
                MessageQueue messageQueue = messageQueueOptional.get();

                Message message = Message.builder()
                        .completed(false)
                        .queue(messageQueue)
                        .body(objectMapper.writeValueAsString(jlmqSendMessage.getBody()))
                        .build();
                repository.save(message);
                messageQueue.getMessages().add(message);
               messageQueueRepository.save(messageQueue);
                return List.of(JlmqReceiveMessage.builder()
                        .messageId(message.getId())
                        .queueName(messageQueue.getName())
                        .body(jlmqSendMessage.getBody())
                        .build());
            } else {
                throw new IllegalArgumentException("no such queue");
            }

        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

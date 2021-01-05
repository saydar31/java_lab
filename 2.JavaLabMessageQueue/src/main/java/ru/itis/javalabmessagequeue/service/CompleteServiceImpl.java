package ru.itis.javalabmessagequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalabmessagequeue.dto.JlmqCompleteMessage;
import ru.itis.javalabmessagequeue.repositories.MessageRepository;

import javax.transaction.Transactional;

@Component
public class CompleteServiceImpl implements CompleteService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    @Transactional
    public void doService(JlmqCompleteMessage completeMessage) {
        messageRepository.setMessageCompletedById(completeMessage.getMessageId());
    }
}

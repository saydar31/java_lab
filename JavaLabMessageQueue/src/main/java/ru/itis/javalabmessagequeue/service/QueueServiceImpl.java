package ru.itis.javalabmessagequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalabmessagequeue.dto.QueueNameDto;
import ru.itis.javalabmessagequeue.dto.SuccessOperationDto;
import ru.itis.javalabmessagequeue.model.MessageQueue;
import ru.itis.javalabmessagequeue.repositories.MessageQueueRepository;

import java.util.Optional;

@Component
public class QueueServiceImpl implements QueueService {
    @Autowired
    private MessageQueueRepository messageQueueRepository;

    @Override
    public SuccessOperationDto createQueue(QueueNameDto queueNameDto) {
        String queueName = queueNameDto.getQueueName();
        Optional<MessageQueue> messageQueueOptional = messageQueueRepository.findById(queueName);
        if (messageQueueOptional.isEmpty()) {
            MessageQueue messageQueue = MessageQueue.builder()
                    .name(queueName)
                    .build();
            messageQueueRepository.save(messageQueue);
            return new SuccessOperationDto("create queue with name " + queueName, true);
        } else {
            return new SuccessOperationDto("create queue with name " + queueName, false);
        }
    }
}

package ru.itis.javalabmessagequeue.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.javalabmessagequeue.dto.JlmqAcceptMessage;

@Slf4j
@Component
public class AcceptServiceImpl implements AcceptService {
    @Override
    public void doService(JlmqAcceptMessage acceptMessage) {
        log.info("message with id="+acceptMessage.getMessageId()+" accepted on remote");
    }
}

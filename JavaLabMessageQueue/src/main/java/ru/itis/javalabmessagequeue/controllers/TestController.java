package ru.itis.javalabmessagequeue.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalabmessagequeue.dto.SuccessOperationDto;
import ru.itis.javalabmessagequeue.model.MessageQueue;
import ru.itis.javalabmessagequeue.repositories.MessageQueueRepository;

import java.util.Optional;

@RestController
@Slf4j
public class TestController {
    @Autowired
    private MessageQueueRepository messageQueueRepository;
    @GetMapping("/test")
    public ResponseEntity<SuccessOperationDto> test(){
        Optional<MessageQueue> messageQueueOptional = messageQueueRepository.findById("defaultQueue");
        messageQueueOptional.ifPresent(messageQueue -> log.info(messageQueue.toString()));
        return ResponseEntity.ok(new SuccessOperationDto("test",true));
    }
}

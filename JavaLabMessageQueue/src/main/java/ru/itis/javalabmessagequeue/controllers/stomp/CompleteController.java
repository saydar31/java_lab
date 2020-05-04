package ru.itis.javalabmessagequeue.controllers.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.itis.javalabmessagequeue.dto.JlmqCompleteMessage;
import ru.itis.javalabmessagequeue.service.CompleteService;

@Controller
public class CompleteController {
    @Autowired
    private CompleteService completeService;

    @MessageMapping("/complete")
    public void complete(JlmqCompleteMessage jlmqCompleteMessage){
        completeService.doService(jlmqCompleteMessage);
    }
}
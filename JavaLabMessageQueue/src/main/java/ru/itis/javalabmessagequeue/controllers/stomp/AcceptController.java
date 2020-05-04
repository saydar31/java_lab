package ru.itis.javalabmessagequeue.controllers.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.itis.javalabmessagequeue.dto.JlmqAcceptMessage;
import ru.itis.javalabmessagequeue.service.AcceptService;

@Controller
public class AcceptController {

    @Autowired
    private AcceptService acceptService;

    @MessageMapping("/accept")
    public void accept(JlmqAcceptMessage acceptMessage){
        acceptService.doService(acceptMessage);
    }
}

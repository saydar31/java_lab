package ru.itis.javalabmessagequeue.controllers.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.itis.javalabmessagequeue.dto.JlmqReceiveMessage;
import ru.itis.javalabmessagequeue.dto.JlmqSendMessage;
import ru.itis.javalabmessagequeue.service.SendService;

import java.util.List;

@Controller
public class SendController {
    @Autowired
    private SendService sendService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void receiveSendMessage(JlmqSendMessage sendMessage) {
        List<JlmqReceiveMessage> receiveMessageList = sendService.doService(sendMessage);
        messagingTemplate.convertAndSend("/topic/queue/" + sendMessage.getQueueName(), receiveMessageList);
    }
}

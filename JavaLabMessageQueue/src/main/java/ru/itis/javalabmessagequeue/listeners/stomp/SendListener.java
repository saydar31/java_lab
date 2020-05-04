package ru.itis.javalabmessagequeue.listeners.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import ru.itis.javalabmessagequeue.dto.JlmqReceiveMessage;
import ru.itis.javalabmessagequeue.service.SubscribeService;

import java.util.List;

@Component
@Slf4j
public class SendListener implements ApplicationListener<SessionSubscribeEvent> {
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
       String destination= (String) sessionSubscribeEvent.getMessage().getHeaders().get("simpDestination");
       if (destination!=null){
           String[] destinationParts = destination.split("/");
           String queueName = destinationParts[3];
           List<JlmqReceiveMessage> receiveMessages = subscribeService.doService(queueName);
           messagingTemplate.convertAndSend(destination,receiveMessages);
       }

    }
}

package ru.itis.javalabmessagequeue.service;

import ru.itis.javalabmessagequeue.dto.JlmqReceiveMessage;
import ru.itis.javalabmessagequeue.dto.JlmqSendMessage;

import java.util.List;

public interface SendService {
    public List<JlmqReceiveMessage> doService(JlmqSendMessage jlmqSendMessage);
}

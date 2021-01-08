package ru.itis.javalabmessagequeue.service;

import ru.itis.javalabmessagequeue.dto.JlmqAcceptMessage;

public interface AcceptService {
    void doService(JlmqAcceptMessage acceptMessage);
}

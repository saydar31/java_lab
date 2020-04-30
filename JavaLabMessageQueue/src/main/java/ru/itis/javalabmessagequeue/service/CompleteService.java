package ru.itis.javalabmessagequeue.service;

import ru.itis.javalabmessagequeue.dto.JlmqCompleteMessage;

public interface CompleteService {
    void doService(JlmqCompleteMessage completeMessage);
}

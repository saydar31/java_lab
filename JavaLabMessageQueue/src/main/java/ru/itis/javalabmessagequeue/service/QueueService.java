package ru.itis.javalabmessagequeue.service;

import ru.itis.javalabmessagequeue.dto.QueueNameDto;
import ru.itis.javalabmessagequeue.dto.SuccessOperationDto;

public interface QueueService {
    SuccessOperationDto createQueue(QueueNameDto queueNameDto);
}

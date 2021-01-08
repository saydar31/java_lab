package ru.itis.javalabmessagequeue.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.javalabmessagequeue.model.MessageQueue;

public interface MessageQueueRepository extends CrudRepository<MessageQueue, String> {
}

package ru.itis.javalabmessagequeue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.javalabmessagequeue.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Modifying
    @Query("update Message msg set msg.completed=true where msg.id=:id")
    void setMessageCompletedById(@Param("id") String id);

    List<Message> findByCompletedFalseAndQueueName(String queueName);
}

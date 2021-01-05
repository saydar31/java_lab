package ru.itis.repositories;

import ru.itis.model.SupportChatMessage;

import java.util.List;

public interface SupportChatMessagesRepository extends CrudRepository<SupportChatMessage, Long> {
    List<SupportChatMessage> getUnreadMessagesByClientId(Long clientId);

    Long getUnreadMessagesCount(Long clientId);

}

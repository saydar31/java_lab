package ru.itis.service;

import ru.itis.model.Message;
import ru.itis.model.Room;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByRoom(Room room);

    void addMessage(String text, Long chatId);
}

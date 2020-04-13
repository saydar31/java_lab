package ru.itis.service;

import org.springframework.security.core.Authentication;
import ru.itis.dto.SupportChatDialogDto;
import ru.itis.dto.SupportChatMessageDto;
import ru.itis.model.User;

import java.util.List;

public interface SupportChatService {
    SupportChatDialogDto getDialogByUser(User user);

    SupportChatDialogDto getDialogByUserId(Long id);

    //for admin
    List<SupportChatMessageDto> getUnreadMessages(Long userId);

    //for user
    List<SupportChatMessageDto> getUnreadMessages(Authentication authentication);

    void addMessage(Long clientId, String text, Authentication authentication);

    void addMessage(String text, Authentication authentication);
}

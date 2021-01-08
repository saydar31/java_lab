package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.itis.dto.SupportChatDialogDto;
import ru.itis.dto.SupportChatMessageDto;
import ru.itis.model.SupportChatDialog;
import ru.itis.model.SupportChatMessage;
import ru.itis.model.User;
import ru.itis.repositories.SupportChatDialogRepository;
import ru.itis.repositories.SupportChatMessagesRepository;
import ru.itis.security.details.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SupportChatServiceImpl implements SupportChatService {
    @Autowired
    private SupportChatMessagesRepository messagesRepository;
    @Autowired
    private SupportChatDialogRepository dialogRepository;

    public static final Map<Long, Object> lockers = new HashMap<>();

    @Override
    public SupportChatDialogDto getDialogByUser(User user) {
        Optional<SupportChatDialog> dialogOptional = dialogRepository.findDialogByUser(user);
        SupportChatDialog dialog;
        if (dialogOptional.isPresent()) {
            dialog = dialogOptional.get();
        } else {
            dialog = SupportChatDialog.builder()
                    .client(user)
                    .containsUnreadMessages(false)
                    .messages(Collections.emptyList())
                    .build();
            dialogRepository.save(dialog);
        }
        return SupportChatDialogDto.from(dialog);
    }

    @Override
    public SupportChatDialogDto getDialogByUserId(Long id) {
        Optional<SupportChatDialog> dialogOptional = dialogRepository.findDialogByUserId(id);
        if (dialogOptional.isPresent()) {
            SupportChatDialog dialog = dialogOptional.get();
            return SupportChatDialogDto.from(dialog);
        }
        throw new IllegalArgumentException("user has nt started");
    }

    @Override
    public List<SupportChatMessageDto> getUnreadMessages(Long userId) {
        if (!lockers.containsKey(userId)) {
            lockers.put(userId, new Object());
        }
        Long unreadMessagesCount = messagesRepository.getUnreadMessagesCount(userId);
        synchronized (lockers.get(userId)) {
            if (unreadMessagesCount.equals(0L)) {
                try {
                    lockers.get(userId).wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException(e);
                }
            }
        }
        List<SupportChatMessage> messages = messagesRepository.getUnreadMessagesByClientId(userId);
        messages.stream()
                .peek(supportChatMessage -> supportChatMessage.setRead(true)).forEach(supportChatMessage -> messagesRepository.update(supportChatMessage));

        return messages.stream()
                .map(SupportChatMessageDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupportChatMessageDto> getUnreadMessages(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return getUnreadMessages(userDetails.getUser().getId());
    }

    public void addMessage(Long clientId, String text, Authentication authentication) {
        Optional<SupportChatDialog> dialogOptional = dialogRepository.findDialogByUserId(clientId);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (dialogOptional.isPresent()) {
            SupportChatDialog dialog = dialogOptional.get();
            SupportChatMessage message = SupportChatMessage.builder()
                    .dateTime(LocalDateTime.now())
                    .dialog(dialog)
                    .isRead(false)
                    .sender(user)
                    .text(text)
                    .build();
            messagesRepository.save(message);
            dialog.getMessages().add(message);
            dialogRepository.update(dialog);
            synchronized (lockers.get(clientId)) {
                lockers.get(clientId).notifyAll();
            }
        }
    }

    public void addMessage(String text, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        addMessage(user.getId(), text, authentication);
    }
}

package ru.itis.repositories;

import ru.itis.model.SupportChatDialog;
import ru.itis.model.User;

import java.util.Optional;

public interface SupportChatDialogRepository extends CrudRepository<SupportChatDialog, Long> {
    Optional<SupportChatDialog> findDialogByUser(User user);

    Optional<SupportChatDialog> findDialogByUserId(Long id);
}

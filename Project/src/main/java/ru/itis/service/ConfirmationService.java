package ru.itis.service;

import ru.itis.dto.UserDto;
import ru.itis.model.User;

public interface ConfirmationService {
    String getVerificationString(User user);
    void verify(String hash);
}

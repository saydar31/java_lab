package ru.itis.service;

import ru.itis.dto.SignInDto;
import ru.itis.model.User;

public interface AuthenticationService {
    boolean checkCookie(String cookie);

    String signIn(SignInDto signInDto);

    User getUserByToken(String token);
}

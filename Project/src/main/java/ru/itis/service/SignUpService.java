package ru.itis.service;

import ru.itis.dto.SignUpDto;
import ru.itis.dto.UserDto;

public interface SignUpService {
    void signUp(String email, String password) throws IllegalArgumentException;

    void signUp(SignUpDto signUpDto);
}

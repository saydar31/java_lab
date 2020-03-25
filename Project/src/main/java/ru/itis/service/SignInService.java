package ru.itis.service;

import ru.itis.dto.SignInDto;
import ru.itis.dto.UserDto;

public interface SignInService {
    UserDto verificationResult(String email, String password) throws IllegalAccessException;
    UserDto verificationResult(SignInDto signInDto) throws IllegalAccessException;
}

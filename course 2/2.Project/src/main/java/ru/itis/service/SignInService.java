package ru.itis.service;

import ru.itis.dto.JwtAuthenticationDto;
import ru.itis.dto.SignInDto;
import ru.itis.dto.UserDto;

public interface SignInService {
    JwtAuthenticationDto verificationResult(SignInDto signInDto) throws IllegalAccessException;
}

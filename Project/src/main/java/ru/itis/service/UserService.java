package ru.itis.service;

import ru.itis.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
}

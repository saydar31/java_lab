package ru.itis.service;

import ru.itis.dto.UserDto;
import ru.itis.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    List<UserDto> getUsers(int page,int size);
    UserDto getUserById(Long id);
}

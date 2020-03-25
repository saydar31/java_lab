package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> result = new ArrayList<>();
        userRepository.getAll().stream().map(UserDto::from).forEach(result::add);
        return result;
    }
}

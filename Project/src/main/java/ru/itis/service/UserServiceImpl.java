package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Qualifier("userRepositoryJdbcTemplateImpl")
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> result = new ArrayList<>();
        userRepository.getAll().stream().map(UserDto::from).forEach(result::add);
        return result;
    }

    @Override
    public List<UserDto> getUsers(int page, int size) {
        List<UserDto> result = new ArrayList<>();
        userRepository.getUsers(page,size).stream().map(UserDto::from).forEach(result::add);
        return result;
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> userOptional = userRepository.find(id);
        return userOptional.map(UserDto::from).orElse(null);
    }


}

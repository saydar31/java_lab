package ru.itis.documents.services;

import org.springframework.stereotype.Component;
import ru.itis.documents.model.User;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public User getCurrentUser() {
        return User.builder()
                .id(1L)
                .firstName("Aydar")
                .lastName("shaydulin")
                .mail("saidar31@yandex.ru")
                .build();
    }
}

package ru.itis.repositories;

import ru.itis.dto.UserDto;
import ru.itis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> getUsers(int page, int size);

    Optional<User> findByEmail(String email);

    void makeProofed(User user);

    UserDto getOneDtoById(Long id);
}

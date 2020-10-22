package ru.itis.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
}

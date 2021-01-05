package ru.itis.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.documents.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}

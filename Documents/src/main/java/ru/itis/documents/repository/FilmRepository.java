package ru.itis.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.documents.model.Film;

public interface FilmRepository extends JpaRepository<Film,Long> {
}

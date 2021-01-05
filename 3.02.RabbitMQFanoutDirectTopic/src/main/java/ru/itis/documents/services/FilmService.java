package ru.itis.documents.services;

import ru.itis.documents.dto.FilmDto;
import ru.itis.documents.model.User;

public interface FilmService {
    void buy(Long filmId, User user);
    void add(FilmDto filmDto);
}

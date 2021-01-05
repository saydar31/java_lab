package ru.itis.documents.services;

import ru.itis.documents.dto.FilmDto;

public interface FilmAdditionService {
    void addShort(FilmDto filmDto);
    void addFullLength(FilmDto filmDto);
}

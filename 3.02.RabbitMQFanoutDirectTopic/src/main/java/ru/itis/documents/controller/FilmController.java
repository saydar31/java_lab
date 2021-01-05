package ru.itis.documents.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.documents.dto.FilmDto;
import ru.itis.documents.model.User;
import ru.itis.documents.services.FilmService;
import ru.itis.documents.services.UserService;

@RestController
@Slf4j
public class FilmController {
    @Autowired
    private FilmService filmService;
    @Autowired
    private UserService userService;

    @PostMapping("/film/{id:\\d+}/buy")
    public void buy(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        filmService.buy(id,user);
    }

    @PostMapping("/film")
    public void addFilm(@RequestBody FilmDto filmDto) {
        filmService.add(filmDto);
    }
}

package ru.itis.documents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.PurchaseDto;
import ru.itis.documents.exceptions.FilmNotFoundException;
import ru.itis.documents.model.Film;
import ru.itis.documents.model.User;
import ru.itis.documents.repository.FilmRepository;
import ru.itis.documents.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class FilmCheckImpl implements FilmCheck {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PdfService pdfService;

    @Override
    public void buy(PurchaseDto purchaseDto) {
        if (purchaseDto.getFilmId() == null) {
            purchaseDto.setFilmId(1L);
        }
        if (purchaseDto.getUserId() == null) {
            purchaseDto.setUserId(1L);
        }
        Optional<User> userOptional = userRepository.findById(purchaseDto.getUserId());
        User user = userOptional.orElse(User.builder()
                .id(1L)
                .firstName("Aydar")
                .lastName("shaydulin")
                .mail("saidar31@yandex.ru")
                .build());

        Optional<Film> filmOptional = filmRepository.findById(purchaseDto.getFilmId());
        Film film = filmOptional.orElseThrow(FilmNotFoundException::new);
        Map<String, Object> model = new HashMap<>();
        model.put("film", film);
        model.put("user", user);
        pdfService.createPdf(model, "filmCheck", "bf-" + UUID.randomUUID().toString() + ".pdf");
    }
}

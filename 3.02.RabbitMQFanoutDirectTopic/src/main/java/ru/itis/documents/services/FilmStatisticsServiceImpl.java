package ru.itis.documents.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.PurchaseDto;
import ru.itis.documents.exceptions.FilmNotFoundException;
import ru.itis.documents.model.Film;
import ru.itis.documents.repository.FilmRepository;

import java.util.Optional;

@Component
@Slf4j
public class FilmStatisticsServiceImpl implements FilmStatisticsService {
    private double sum;
    private int count;
    @Autowired
    private FilmRepository filmRepository;

    @Override
    public void makeStatistics(PurchaseDto purchaseDto) {
        if (purchaseDto.getFilmId()==null){
            purchaseDto.setFilmId(1L);
        }
        Optional<Film> filmOptional = filmRepository.findById(purchaseDto.getFilmId());
        Film film = filmOptional.orElseThrow(FilmNotFoundException::new);
        sum += film.getPrice();
        count++;
        if (count % 10 == 0) {
            double av = sum / count;
            log.info("average: " + av);
        }
    }

    @Override
    public double getPriceAverage() {
        return count == 0 ? 0 : sum / count;
    }
}

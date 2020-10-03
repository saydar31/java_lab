package ru.itis.documents.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.FilmDto;
import ru.itis.documents.dto.PurchaseDto;
import ru.itis.documents.services.FilmAdditionService;
import ru.itis.documents.services.FilmCheck;
import ru.itis.documents.services.FilmStatisticsService;

@Component
public class FilmConsumer {
    @Autowired
    private FilmCheck filmCheck;
    @Autowired
    private FilmStatisticsService filmStatisticsService;
    @Autowired
    private FilmAdditionService additionService;

    @RabbitListener(queues = "film_purchase")
    public void makePurchaseChek(PurchaseDto purchaseDto) {
        filmCheck.buy(purchaseDto);
    }

    @RabbitListener(queues = "film_purchase_statistics")
    public void collectStatistics(PurchaseDto purchaseDto) {
        filmStatisticsService.makeStatistics(purchaseDto);
    }

    @RabbitListener(queues = "short_film_queue")
    public void addShortFilm(FilmDto filmDto) {
        additionService.addShort(filmDto);
    }

    @RabbitListener(queues = "full_length_film_queue")
    public void addFullLengthFilm(FilmDto filmDto) {
        additionService.addFullLength(filmDto);
    }
}

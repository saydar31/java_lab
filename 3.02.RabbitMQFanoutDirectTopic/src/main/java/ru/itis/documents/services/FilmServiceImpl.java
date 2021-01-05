package ru.itis.documents.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.FilmDto;
import ru.itis.documents.dto.PurchaseDto;
import ru.itis.documents.model.User;
@Component
public class FilmServiceImpl implements FilmService {
    @Autowired
    @Qualifier("customRabbitTemplate")
    private AmqpTemplate amqpTemplate;
    @Override
    public void buy(Long filmId, User user) {
        PurchaseDto purchaseDto = PurchaseDto.builder()
                .filmId(filmId)
                .userId(user.getId())
                .build();
        amqpTemplate.convertAndSend("filmPurchase","",purchaseDto);
    }

    @Override
    public void add(FilmDto filmDto) {
        if (filmDto.getDuration()>=90*60*1000){
            amqpTemplate.convertAndSend("film_add","full_length",filmDto);
        }else {
            amqpTemplate.convertAndSend("film_add","short",filmDto);
        }
    }
}

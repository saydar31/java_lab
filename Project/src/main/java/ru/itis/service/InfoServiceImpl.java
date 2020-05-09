package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.InfoDto;
@Component
public class InfoServiceImpl implements InfoService{
    @Autowired
    private Translator translator;
    @Override
    public InfoDto getInfo() {
        return InfoDto.builder()
                .info(translator.getTranslation("info"))
                .build();
    }
}

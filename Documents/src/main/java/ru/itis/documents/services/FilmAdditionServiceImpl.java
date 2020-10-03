package ru.itis.documents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.FilmDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FilmAdditionServiceImpl implements FilmAdditionService {
    @Autowired
    private PdfService pdfService;
    @Override
    public void addShort(FilmDto filmDto) {
        Map<String,Object> model=new HashMap<>();
        model.put("film",filmDto);
        pdfService.createPdf(model,"shortFilm", UUID.randomUUID().toString()+".pdf");
    }

    @Override
    public void addFullLength(FilmDto filmDto) {
        Map<String,Object> model=new HashMap<>();
        model.put("film",filmDto);
        pdfService.createPdf(model,"longFilm", UUID.randomUUID().toString()+".pdf");
    }
}

package ru.itis.contractgenerator.services;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.contractgenerator.model.Contract;
import ru.itis.contractgenerator.model.PersonInfo;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Slf4j
public class PdfServiceImpl implements PdfService {
    @Value("${storage}")
    private String storage;

    private String repairCyrillic(String s){
        String myString = "some cyrillic text";
        byte bytes[] = myString.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    @Override
    public void createContract(Contract contract) {
        String fileName = storage + File.separator + UUID.randomUUID().toString() + ".pdf";
        File file = new File(fileName);
        file.createNewFile();
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont("times_new_roman.ttf");
        document.add(new Paragraph(contract.getHeader()).setFont(font));
        PersonInfo personInfo = contract.getPersonInfo();
        document.add(new Paragraph("Name:" + personInfo.getFirstName()));
        document.add(new Paragraph("Surname:" + personInfo.getLastName()));
        document.add(new Paragraph("Age:" + personInfo.getAge()));
        document.add(new Paragraph("id:" + personInfo.getIdNumber()));
        document.add(new Paragraph("id issue date:" + personInfo.getIdIssueDate().getYear() + "-" + personInfo.getIdIssueDate().getMonth() + "-" + personInfo.getIdIssueDate().getDayOfMonth()));
        document.close();
        log.info(fileName+" is ready");
    }
}

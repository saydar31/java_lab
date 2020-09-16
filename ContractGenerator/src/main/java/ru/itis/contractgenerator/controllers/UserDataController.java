package ru.itis.contractgenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.contractgenerator.model.PersonInfo;
import ru.itis.contractgenerator.producer.ContractProducer;

import java.time.LocalDate;

@Component
public class UserDataController {
    @Autowired
    private ContractProducer contractProducer;

    public void makeContracts(String inputString) {
        String[] data = inputString.split(";");
        if (data.length == 5) {
            PersonInfo personInfo = PersonInfo.builder()
                    .firstName(data[0])
                    .lastName(data[1])
                    .age(Integer.parseInt(data[2]))
                    .idNumber(data[3])
                    .idIssueDate(LocalDate.parse(data[4]))
                    .build();
            contractProducer.produceContract(personInfo);
        } else {
            throw new IllegalArgumentException();
        }
    }
}

package ru.itis.contractgenerator.producer;

import ru.itis.contractgenerator.model.PersonInfo;

public interface ContractProducer {
    void produceContract(PersonInfo personInfo);
}

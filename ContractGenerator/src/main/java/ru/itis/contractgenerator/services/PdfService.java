package ru.itis.contractgenerator.services;

import ru.itis.contractgenerator.model.Contract;

public interface PdfService {
    void createContract(Contract contract);
}

package ru.itis.documents.services;

import ru.itis.documents.dto.PurchaseDto;

public interface FilmStatisticsService {
    void makeStatistics(PurchaseDto purchaseDto);
    double getPriceAverage();
}

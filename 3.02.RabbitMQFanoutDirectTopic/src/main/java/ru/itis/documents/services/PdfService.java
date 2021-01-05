package ru.itis.documents.services;

import java.util.Map;

public interface PdfService {
    void createPdf(Map<String, Object> data, String templateName, String fileName);
}

package ru.itis.hateoas.services;

import ru.itis.hateoas.models.Report;

public interface ReportService {
    Report satisfy(Long reportId);

    Report reject(Long reportId);
}

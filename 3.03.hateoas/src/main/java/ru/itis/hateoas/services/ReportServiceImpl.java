package ru.itis.hateoas.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.hateoas.exceptions.ReportNotFoundException;
import ru.itis.hateoas.models.*;
import ru.itis.hateoas.repositories.ReportRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    private Report getReportFromRepository(Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        return reportOptional.orElseThrow(ReportNotFoundException::new);
    }

    @Override
    @Transactional
    public Report satisfy(Long reportId) {
        Report report = getReportFromRepository(reportId);
        Course course = report.getCourse();
        if (report.getReportAction().equals(ReportAction.BAN)) {
            course.setAccessLevel(AccessLevel.BANNED);
        } else {
            course.setAccessLevel(AccessLevel.SUBSCRIBER_ONLY);
        }
        report.setReportStatus(ReportStatus.SATISFIED);
        return report;
    }

    @Override
    @Transactional
    public Report reject(Long reportId) {
        Report report = getReportFromRepository(reportId);
        report.setReportStatus(ReportStatus.REJECTED);
        return report;
    }
}

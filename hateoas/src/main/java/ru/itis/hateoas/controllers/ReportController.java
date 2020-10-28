package ru.itis.hateoas.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import ru.itis.hateoas.services.ReportService;

@RepositoryRestController
@AllArgsConstructor
public class ReportController {

    private ReportService reportService;

    @PutMapping("/reports/{report-id:\\d+}/satisfy")
    public ResponseEntity<?> satisfy(@PathVariable("report-id") Long id) {
        return ResponseEntity.ok(EntityModel.of(reportService.satisfy(id)));
    }
    @PutMapping("/reports/{report-id:\\d+}/reject")
    public ResponseEntity<?> reject(@PathVariable("report-id") Long id) {
        return ResponseEntity.ok(EntityModel.of(reportService.reject(id)));
    }
}

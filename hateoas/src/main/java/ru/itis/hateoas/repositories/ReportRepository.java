package ru.itis.hateoas.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.hateoas.models.Report;

import java.util.List;
@RepositoryRestResource
public interface ReportRepository extends JpaRepository<Report, Long> {
    @RestResource(path = "unreviewed",rel = "unreviewed")
    @Query("select report from Report report where report.reportStatus=ru.itis.hateoas.models.ReportStatus.UNREVIEWED")
    List<Report> findAllUnreviewed(Pageable pageable);
}

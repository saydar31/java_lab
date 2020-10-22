package ru.itis.hateoas.configs.representationprocessors;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.hateoas.controllers.ReportController;
import ru.itis.hateoas.models.Report;
import ru.itis.hateoas.models.ReportStatus;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class ReportRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Report>> {
    private final RepositoryEntityLinks links;

    @Override
    public EntityModel<Report> process(EntityModel<Report> model) {
        Report report = model.getContent();
        if (report!=null&&report.getReportStatus().equals(ReportStatus.UNREVIEWED)){
            model.add(linkTo(methodOn(ReportController.class).satisfy(report.getId())).withRel("satisfy"));
        }
        return model;
    }
}

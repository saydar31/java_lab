package ru.itis.hateoas.configs.representationprocessors;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.hateoas.controllers.VoteController;
import ru.itis.hateoas.models.Course;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class VoteRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Course>> {
    private final RepositoryEntityLinks links;

    @Override
    public EntityModel<Course> process(EntityModel<Course> model) {
        Course course = model.getContent();
        if (course != null) {
            model.add(linkTo(methodOn(VoteController.class).upvote(course.getId())).withRel("upvote"));
            model.add(linkTo(methodOn(VoteController.class).downvote(course.getId())).withRel("downvote"));
        }
        return model;
    }
}

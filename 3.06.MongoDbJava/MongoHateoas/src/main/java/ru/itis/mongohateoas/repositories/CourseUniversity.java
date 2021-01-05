package ru.itis.mongohateoas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.mongohateoas.model.Course;

import java.util.List;

public interface CourseUniversity extends MongoRepository<Course, String> {
    @RestResource(path = "rating", rel = "rating_grater")
    List<Course> findCourseByRatingGreaterThan(Long rating);

}

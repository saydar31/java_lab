package ru.itis.dsl.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.itis.dsl.model.Course;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String>, QuerydslPredicateExecutor<Course> {
    List<Course> findCourseByRatingGreaterThan(Long rating);

}

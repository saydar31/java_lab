package ru.itis.springmongojpa.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.springmongojpa.model.Course;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {
    @Query("{rating: {$gt: ?0}}")
    List<Course> findCourseWhereRatingGrater(@Param("rating") Long rating);
}

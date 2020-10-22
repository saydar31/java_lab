package ru.itis.hateoas.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.itis.hateoas.models.Course;
import ru.itis.hateoas.models.University;

import java.util.List;

@RepositoryRestResource
public interface CourseRepository extends JpaRepository<Course, Long> {

    @RestResource(path = "open",rel = "open")
    @Query("select course from Course  course where course.accessLevel=ru.itis.hateoas.models.AccessLevel.OPEN")
    List<Course> findAllOpen(Pageable pageable);

    @RestResource(path = "university",rel = "university")
    List<Course> findAllByUniversity(University university);

}

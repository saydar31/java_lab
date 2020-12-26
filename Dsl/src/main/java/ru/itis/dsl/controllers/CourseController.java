package ru.itis.dsl.controllers;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.dsl.dto.CourseDto;
import ru.itis.dsl.model.Course;
import ru.itis.dsl.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Controller("/courses/")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/search")
    public ResponseEntity<?> find(@QuerydslPredicate(root = Course.class) Predicate predicate){
        Iterable<Course> courses = courseRepository.findAll(predicate);
        List<CourseDto> dtos = new ArrayList<>();
        for (Course course :
                courses) {
            dtos.add(new CourseDto(course.get_id(),course.getName(),course.getRating()));
        }
        return ResponseEntity.ok(dtos);
    }
}

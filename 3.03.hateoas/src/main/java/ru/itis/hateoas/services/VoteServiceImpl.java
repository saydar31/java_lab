package ru.itis.hateoas.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.hateoas.exceptions.CourseNotFoundException;
import ru.itis.hateoas.models.Course;
import ru.itis.hateoas.repositories.CourseRepository;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public Course upvote(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        course.upvote();
        return course;
    }

    @Transactional
    @Override
    public Course downvote(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        course.downvote();
        return course;
    }
}

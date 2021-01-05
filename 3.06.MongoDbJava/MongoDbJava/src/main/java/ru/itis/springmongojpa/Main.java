package ru.itis.springmongojpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.springmongojpa.config.RepositoriesConfig;
import ru.itis.springmongojpa.model.Course;
import ru.itis.springmongojpa.model.University;
import ru.itis.springmongojpa.repositories.CourseRepository;
import ru.itis.springmongojpa.repositories.UniversityRepository;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RepositoriesConfig.class);
        UniversityRepository universityRepository = context.getBean(UniversityRepository.class);
        CourseRepository courseRepository = context.getBean(CourseRepository.class);
//        Course course = Course.builder()
//                .name("English")
//                .rating(11L)
//                .build();
//        Course course1 = Course.builder()
//                .name("Old English")
//                .rating(9L)
//                .build();
//        University university = University.builder()
//                .name("Oxford")
//                .country("UK")
//                .directions(Arrays.asList("philology","computer science","philosophy"))
//                .build();
//        universityRepository.save(university);
//        course.setUniversity(university);
//        course1.setUniversity(university);
//        courseRepository.save(course);
//        courseRepository.save(course1);
        //universityRepository.save(university);
        List<Course> courseWhereRatingGrater = courseRepository.findCourseWhereRatingGrater(10L);
        System.out.println(courseWhereRatingGrater.size());
    }
}

package ru.itis.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hateoas.models.AccessLevel;
import ru.itis.hateoas.models.Course;
import ru.itis.hateoas.models.University;
import ru.itis.hateoas.repositories.CourseRepository;
import ru.itis.hateoas.repositories.UniversityRepository;
import ru.itis.hateoas.services.VoteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
@AutoConfigureMockMvc
public class UpvoteTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        Course course = course();
        University university=course.getUniversity();
        university.setCourses(null);
        universityRepository.save(university);
        courseRepository.save(course);
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(put("/courses/1/upvote"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.rate").value(1001));
    }

    private Course course() {
        University university = University.builder()
                .city("Kazan")
                .country("Russia")
                .name("KFU")
                .build();
        Course course = Course.builder()
                .accessLevel(AccessLevel.OPEN)
                .name("Java")
                .rate(1000)
                .university(university)
                .build();
        List<Course> courses = new ArrayList<>(1);
        courses.add(course);
        university.setCourses(courses);
        return course;
    }
}

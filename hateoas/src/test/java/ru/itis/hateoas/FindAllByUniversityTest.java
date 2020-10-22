package ru.itis.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hateoas.models.AccessLevel;
import ru.itis.hateoas.models.Course;
import ru.itis.hateoas.models.University;
import ru.itis.hateoas.repositories.CourseRepository;
import ru.itis.hateoas.repositories.UniversityRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class FindAllByUniversityTest {
    private University university = University.builder()
            .city("Kazan")
            .country("Russia")
            .name("KFU")
            .build();
    private Course java = Course.builder()
            .accessLevel(AccessLevel.SUBSCRIBER_ONLY)
            .name("Технологии Java")
            .rate(12)
            .build();
    private Course algebra = Course.builder()
            .rate(6)
            .name("Algebra")
            .accessLevel(AccessLevel.OPEN)
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        universityRepository.save(university);
        java.setUniversity(university);
        algebra.setUniversity(university);
        courseRepository.save(java);
        courseRepository.save(algebra);
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/courses/search/university?university=%2Funiversities%2F1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courses", hasSize(2)));
    }
}

package ru.itis.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hateoas.models.*;
import ru.itis.hateoas.repositories.CourseRepository;
import ru.itis.hateoas.repositories.ReportRepository;
import ru.itis.hateoas.repositories.UniversityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class FindAllUnreviewedTest {

    private Report report = Report.builder()
            .reportStatus(ReportStatus.UNREVIEWED)
            .reportAction(ReportAction.SUBSCRIPTION_ONLY)
            .email("mail@mail.ru")
            .build();
    private Report banReport = Report.builder()
            .reportStatus(ReportStatus.UNREVIEWED)
            .reportAction(ReportAction.BAN)
            .email("mail@mail.ru")
            .build();
    private Report rejectedReport = Report.builder()
            .email("rej@mail.ru")
            .reportAction(ReportAction.SUBSCRIPTION_ONLY)
            .reportStatus(ReportStatus.REJECTED)
            .build();
    private Course course = Course.builder()
            .name("Algebra")
            .accessLevel(AccessLevel.OPEN)
            .build();
    private University university = University.builder()
            .name("KFU")
            .country("Russia")
            .city("Kazan")
            .build();

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        universityRepository.save(university);
        course.setUniversity(university);
        courseRepository.save(course);
        report.setCourse(course);
        banReport.setCourse(course);
        rejectedReport.setCourse(course);
        List<Course> courses = new ArrayList<>(1);
        courses.add(course);
        university.setCourses(courses);
        reportRepository.save(report);
        reportRepository.save(banReport);
        reportRepository.save(rejectedReport);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/reports/search/unreviewed").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reports", hasSize(2)))
                .andDo(document("unreviewed"));
    }
}

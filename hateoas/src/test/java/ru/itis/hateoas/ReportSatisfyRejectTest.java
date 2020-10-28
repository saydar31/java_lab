package ru.itis.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hateoas.models.*;
import ru.itis.hateoas.services.ReportService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class ReportSatisfyRejectTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @BeforeEach()
    public void setUp() {
        when(reportService.satisfy(1L)).thenReturn(satisfiedReport());
        when(reportService.reject(2L)).thenReturn(rejectedReport());
    }

    @Test
    public void testSatisfy() throws Exception {
        mockMvc.perform(put("/reports/1/satisfy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(satisfiedReport().getEmail()))
                .andExpect(jsonPath("$.reportAction").value(satisfiedReport().getReportAction().name()))
                .andExpect(jsonPath("$.reportStatus").value(satisfiedReport().getReportStatus().name()))
                .andDo(document("satisfy", responseFields(
                        fieldWithPath("email").description("почта куда отправится сообещение об отказе"),
                        fieldWithPath("reportAction").description("Действие жалобы: \"SUBSCRIPTION_ONLY\"(открыть доступ по подписке), \"BAN\"(закрыть доступ)"),
                        fieldWithPath("reportStatus").description("Статус жалобы: \"SATISFIED\"(удовлетварена), \"REJECTED\"(отказано),\"UNREVIEWED\" не рассмотрена")
                )));
    }

    @Test
    public void testReject() throws Exception {
        Report rejectedReport = rejectedReport();
        mockMvc.perform(put("/reports/2/reject"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(rejectedReport.getEmail()))
                .andExpect(jsonPath("$.reportAction").value(rejectedReport.getReportAction().name()))
                .andExpect(jsonPath("$.reportStatus").value(rejectedReport.getReportStatus().name()))
                .andDo(document("reject", responseFields(
                        fieldWithPath("email").description("почта куда отправится сообещение об отказе"),
                        fieldWithPath("reportAction").description("Действие жалобы: \"SUBSCRIPTION_ONLY\"(открыть доступ по подписке), \"BAN\"(закрыть доступ)"),
                        fieldWithPath("reportStatus").description("Статус жалобы: \"SATISFIED\"(удовлетварена), \"REJECTED\"(отказано),\"UNREVIEWED\" не рассмотрена")
                )));
    }

    private Report satisfiedReport() {
        Report report = Report.builder()
                .id(1L)
                .email("mail@mail.ru")
                .reportAction(ReportAction.SUBSCRIPTION_ONLY)
                .reportStatus(ReportStatus.SATISFIED)
                .build();
        Course course = Course.builder()
                .accessLevel(AccessLevel.OPEN)
                .name("Algebra")
                .id(2L)
                .build();
        List<Course> courses = new ArrayList<>(1);
        courses.add(course);
        University university = University.builder()
                .city("Kazan")
                .country("Russia")
                .courses(courses)
                .name("KFU")
                .build();
        course.setUniversity(university);
        report.setCourse(course);
        return report;
    }

    private Report rejectedReport() {
        Report report = satisfiedReport();
        report.setReportStatus(ReportStatus.REJECTED);
        return report;
    }
}

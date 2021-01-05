package ru.itis.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.template.config.TemplateConfiguration;
import ru.itis.template.model.University;
import ru.itis.template.repositories.UniversityRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TemplateConfiguration.class);
        UniversityRepository universityRepository = applicationContext.getBean(UniversityRepository.class);
        List<University> byNameLike = universityRepository.findByNameLike("KF");
        System.out.println(byNameLike.size());
    }
}

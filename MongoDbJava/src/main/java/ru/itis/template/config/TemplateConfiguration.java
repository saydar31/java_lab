package ru.itis.template.config;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ComponentScan("ru.itis.template")
public class TemplateConfiguration {
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(), "universityies");
    }
}

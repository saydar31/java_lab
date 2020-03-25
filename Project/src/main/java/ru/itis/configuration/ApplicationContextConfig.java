package ru.itis.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationContextConfig {

    @Autowired
    Environment environment;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setUsername(environment.getProperty("db.user"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver"));
        return hikariConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public freemarker.template.Configuration configuration() {
        freemarker.template.Configuration conf = new freemarker.template.Configuration(new Version(2, 3, 29));
        conf.setClassForTemplateLoading(ApplicationContextConfig.class, "/templates/");
        return conf;
    }

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256("mr brightside");
    }

    @Bean
    public Session session() {
        Properties properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("email.properties")));
            return Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("ffada15e8130c4", "1dba7b81455072");
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean(name = "storagePath")
    public String storagePath() {
        return environment.getProperty("storage.path");
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/pages/");
        return freeMarkerConfigurer;
    }

    @Bean(name = "freeMarkerViewResolver")
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        viewResolver.setContentType("text/html; charset=UTF-8");
        return viewResolver;
    }

}

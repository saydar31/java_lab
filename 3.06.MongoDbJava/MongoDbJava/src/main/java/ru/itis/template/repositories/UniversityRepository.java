package ru.itis.template.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ru.itis.template.model.University;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class UniversityRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public UniversityRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<University> findByNameLike(String name) {
        return mongoTemplate.find(new Query(where("name").regex(Pattern.compile("^" + name + ".*"))), University.class);
    }

}

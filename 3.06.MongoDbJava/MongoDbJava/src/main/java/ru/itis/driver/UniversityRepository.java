package ru.itis.driver;

import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import ru.itis.driver.model.Course;
import ru.itis.driver.model.University;

import java.util.*;
import java.util.stream.Collectors;


public class UniversityRepository {
    private MongoCollection<Document> collection;

    public UniversityRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void save(University university) {
        Document document = new Document();
        document.append("country", university.getCountry());
        document.append("directions", university.getDirections());
        document.append("name", university.getName());
        document.append("courses", university.getCourses());
        collection.insertOne(document);
    }

    public Optional<University> findById(String id) {
        Document searchQuery = new Document();
        searchQuery.append("_id", new ObjectId(id));
        Document document = collection.find(searchQuery).first();
        if (document != null) {
            return Optional.of(map(document));
        } else {
            return Optional.empty();
        }
    }

    public List<University> findByCountry(String country) {
        Document searchQuery = new Document();
        searchQuery.append("country", country);
        FindIterable<Document> documents = collection.find(searchQuery);
        List<University> universities = new ArrayList<>();
        for (Document document : documents) {
            universities.add(map(document));
        }
        return universities;
    }

    public List<University> findByDirections(List<String> directions){
        Document searchQuery = new Document();
        searchQuery.append("directions", directions);
        FindIterable<Document> documents = collection.find(searchQuery);
        List<University> universities = new ArrayList<>();
        for (Document document : documents) {
            universities.add(map(document));
        }
        return universities;
    }

    private University map(Document document) {
        University university = new University();
        university.set_id(document.getObjectId("_id").toString());
        university.setCountry(document.getString("country"));
        university.setDirections(document.getList("directions", String.class));
        List<Map> maps = document.getList("courses", Map.class);
        if (maps != null){
            List<Course> courses = maps.stream().map(map -> {
                String name = (String) map.get("name");
                Long rating = (Long) map.get("rating");
                return new Course(name, rating);
            }).collect(Collectors.toList());
            university.setCourses(courses);
        }

        return university;
    }

}

package ru.itis.driver;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.itis.driver.model.Course;
import ru.itis.driver.model.University;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase database = client.getDatabase("lecturehub").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Document> documents = database.getCollection("universityies");
        UniversityRepository universityRepository = new UniversityRepository(documents);
//        Course course = new Course("Java", 10L);
//        Course course2 = new Course("JS", 11L);
//        University university = University.builder()
//                .country("USA")
//                .name("MIT")
//                .directions(Arrays.asList("philology", "philosophy", "math", "chemistry"))
//                .courses(Arrays.asList(course, course2))
//                .build();
//        universityRepository.save(university);
        Optional<University> universityOptional = universityRepository.findById("5fe734ed44ee9d0e616e1ffa");
        System.out.println(universityOptional.orElseThrow(IllegalArgumentException::new));

        List<University> byDirections = universityRepository.findByDirections(Collections.singletonList("philology"));
        for (University u :
                byDirections) {
            System.out.println(u);
        }
    }
}

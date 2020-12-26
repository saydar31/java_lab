package ru.itis.springmongojpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "universityies")
public class University {
    @Id
    private String _id;
    private String name;
    private String country;
    private List<String> directions;
    @DBRef
    private List<Course> courses;
}

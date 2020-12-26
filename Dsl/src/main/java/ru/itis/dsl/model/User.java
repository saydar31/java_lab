package ru.itis.dsl.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String _id;
    private String name;
    private String lastName;
    private String login;

    @DBRef
    private List<Course> courses;
}

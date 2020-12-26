package ru.itis.mongohateoas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "courses")
public class Course {
    @Id
    private String _id;
    private String name;
    private University university;
    private Long rating;
    private Boolean isFree;
}

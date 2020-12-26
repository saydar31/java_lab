package ru.itis.driver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class University {
    private String _id;
    private String name;
    private String country;
    private List<String> directions;
    private List<Course> courses;
}

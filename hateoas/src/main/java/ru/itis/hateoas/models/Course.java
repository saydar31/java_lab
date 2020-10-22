package ru.itis.hateoas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private University university;
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    private Integer rate;

    public void upvote(){
        rate++;
    }

    public  void downvote(){
        rate--;
    }
}

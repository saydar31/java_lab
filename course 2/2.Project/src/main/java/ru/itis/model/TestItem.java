package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "test_items")
public class TestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    @Column(name = "parameter_name")
    private String parameterName;
    @Column(name = "is_multiple_choice")
    private boolean isMultipleChoice;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> answers;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Test test;
}

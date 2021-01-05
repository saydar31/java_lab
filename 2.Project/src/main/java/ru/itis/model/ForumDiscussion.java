package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "forum_discussions")
public class ForumDiscussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> tags;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ForumDiscussionRecord> records;
    @ManyToOne
    private User owner;
    private LocalDateTime lastChange;
    @Transient
    private String oneLineTags;

    @PostLoad
    public void concatTags() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tags) {
            stringBuilder.append(tag).append(";");
        }
        oneLineTags = stringBuilder.toString();
    }
}

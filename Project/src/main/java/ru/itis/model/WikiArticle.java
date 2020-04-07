package ru.itis.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "wiki_article")
@EqualsAndHashCode(exclude = "folder")
public class WikiArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private User author;
    @OneToOne
    private WikiArticleVersion currentVersion;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<WikiArticleVersion> versions;
    @ManyToOne
    private WikiFolder folder;
}

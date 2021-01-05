package ru.itis.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EqualsAndHashCode(exclude = "parentFolder")
@Table(name = "wiki_folder")
public class WikiFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private WikiFolder parentFolder;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<WikiFolder> childFolders;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<WikiArticle> articles;
}

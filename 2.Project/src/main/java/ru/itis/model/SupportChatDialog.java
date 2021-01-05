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
@EqualsAndHashCode(exclude = "messages")
@Entity
public class SupportChatDialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SupportChatMessage> messages;
    private boolean containsUnreadMessages;
    @OneToOne
    private User client;
}

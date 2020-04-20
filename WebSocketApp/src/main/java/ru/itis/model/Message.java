package ru.itis.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = "room")
@ToString(exclude = "room")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private LocalDateTime dateTime;
    @ManyToOne
    private User author;
    @ManyToOne
    private Room room;
}

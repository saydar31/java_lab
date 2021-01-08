package ru.itis.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(exclude = "dialog")
@ToString(exclude = "dialog")
@Entity
public class SupportChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private SupportChatDialog dialog;
    private String text;
    private boolean isRead;
    private LocalDateTime dateTime;
}

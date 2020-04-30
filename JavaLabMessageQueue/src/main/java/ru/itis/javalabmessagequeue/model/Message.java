package ru.itis.javalabmessagequeue.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude ="queue")
@Entity
public class Message {
    @Id
    @GenericGenerator(name = "uuid",strategy = "ru.itis.javalabmessagequeue.generator.MessageIdGenerator")
    @GeneratedValue(generator = "uuid")
    private String id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private MessageQueue queue;
    @Column(length = 1000)
    private String body;
    private boolean completed;
}

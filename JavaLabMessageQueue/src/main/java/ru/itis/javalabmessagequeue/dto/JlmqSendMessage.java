package ru.itis.javalabmessagequeue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JlmqSendMessage {
    private final String command = "send";
    private String queueName;
    private Object body;
}

package ru.itis.javalabmessagequeue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JlmqCompleteMessage {
    private final String command ="complete";
    private String messageId;
}

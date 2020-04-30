package ru.itis.javalabmessagequeue.sdk.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JlmqSendMessage {
    private final String command = "send";
    private String queueName;
    private Object body;
}

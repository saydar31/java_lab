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
public class JlmqReceiveMessage {
    private final String command = "receive";
    private String queueName;
    private String messageId;
    private Object body;
}

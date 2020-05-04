package ru.itis.javalabmessagequeue.sdk.stomp.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

package ru.itis.javalabmessagequeue.sdk.stomp.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JlmqStompSendMessage  implements Serializable {
    private final String command = "send";
    private String queueName;
    private Object body;
}

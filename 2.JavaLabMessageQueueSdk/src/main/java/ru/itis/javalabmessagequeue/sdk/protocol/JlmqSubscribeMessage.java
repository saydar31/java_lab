package ru.itis.javalabmessagequeue.sdk.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
//consumer->Jlmq
public class JlmqSubscribeMessage {
    private final String command = "subscribe";
    private String queueName;
}

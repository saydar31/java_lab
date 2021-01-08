package ru.itis.javalabmessagequeue.sdk.stomp.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JlmqAcceptMessage {
    private final String command ="accept";
    private String messageId;
}

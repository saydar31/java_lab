package ru.itis.javalabmessagequeue.sdk.stomp.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JlmqConfiguration {
    private String url;
    private Integer connectionsCount;
    private String authenticationKey;
}

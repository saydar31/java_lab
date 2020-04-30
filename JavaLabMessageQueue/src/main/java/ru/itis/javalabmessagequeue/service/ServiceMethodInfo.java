package ru.itis.javalabmessagequeue.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceMethodInfo {
    private String beanClassName;
    private String methodName;
    private List<String> argClassNames;
    private boolean returning;
}

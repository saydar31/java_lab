package ru.itis.javalabmessagequeue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class ServiceMapperClasspathImpl implements ServiceMapper {
    private final ObjectMapper objectMapper;
    private Map<String, ServiceMethodInfo> map;
    @Autowired
    private ApplicationContext applicationContext;

    public ServiceMapperClasspathImpl(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mappings/serviceMapping.json");
        map = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
    }

    private Class[] classListToArray(List<Class<?>> classList) {
        Class[] result = new Class[classList.size()];
        for (int i = 0; i < classList.size(); i++) {
            result[i] = classList.get(i);
        }
        return result;
    }

    @Override
    public Optional<Object> getServiceResult(String command, String json, WebSocketSession session) {
        ServiceMethodInfo methodInfo = map.get(command);
        try {
            Class<?> beanClass = Class.forName(methodInfo.getBeanClassName());
            Object serviceBean = applicationContext.getBean(beanClass);
            List<Class<?>> argClasses = new ArrayList<>();
            for (int i = 0; i < methodInfo.getArgClassNames().size(); i++) {
                argClasses.add(Class.forName(methodInfo.getArgClassNames().get(i)));
            }
            Class[] argClassesArray = classListToArray(argClasses);
            Method method = beanClass.getMethod(methodInfo.getMethodName(), argClassesArray);
            Object[] argsToInvoke = new Object[argClassesArray.length];
            argsToInvoke[0] = objectMapper.readValue(json, argClasses.get(0));
            if (argClasses.size() > 1 && argClasses.get(1).equals(WebSocketSession.class)) {
                argsToInvoke[1] = session;
            }
            Object methodResult = method.invoke(serviceBean, argsToInvoke);
            if (methodInfo.isReturning()) {
                return Optional.of(methodResult);
            } else {
                return Optional.empty();
            }
        } catch (ClassNotFoundException | NoSuchMethodException | JsonProcessingException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

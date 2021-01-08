package ru.itis.javalabmessagequeue.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface Invokable {
    Optional<Object> invoke(Object... args) throws InvocationTargetException, IllegalAccessException;
}

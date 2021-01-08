package ru.itis.javalabmessagequeue.service;

public interface AuthenticationService {
    boolean authenticate(String key);
    void setKey(String key);
}

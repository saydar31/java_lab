package ru.itis.javalabmessagequeue.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private String key;

    public AuthenticationServiceImpl() {
        this.key = randomAlphaNumeric();
        log.info("authentication key is " + key);
    }

    private String randomAlphaNumeric() {
        String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOP123456789_";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            stringBuilder.append(charSet.charAt(random.nextInt(charSet.length() - 1)));
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean authenticate(String key) {
        return this.key.equals(key);
    }

    @Override
    public void setKey(String key) {
        this.key = key;
        log.info("authentication key changed to " + key);
    }
}

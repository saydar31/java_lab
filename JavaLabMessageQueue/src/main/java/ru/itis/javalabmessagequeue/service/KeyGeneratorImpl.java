package ru.itis.javalabmessagequeue.service;

import org.springframework.stereotype.Component;

@Component
public class KeyGeneratorImpl implements KeyGenerator{

    @Override
    public String getKey() {
        return "fehsnbsahcbahchasmbc";
    }
}

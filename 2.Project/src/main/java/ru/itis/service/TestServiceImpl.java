package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.model.Test;
import ru.itis.repositories.TestRepository;

import java.util.Optional;

@Component
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository testRepository;

    @Override
    public Test getTestByName(String name) {
        Optional<Test> testOptional = testRepository.findByName(name);
        if (testOptional.isPresent()) {
            return testOptional.get();
        }else {
            throw new IllegalArgumentException();
        }
    }
}

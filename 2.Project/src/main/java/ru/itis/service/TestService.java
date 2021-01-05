package ru.itis.service;

import ru.itis.model.Test;

public interface TestService {
    Test getTestByName(String name);
}

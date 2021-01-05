package ru.itis.documents.services;

import ru.itis.documents.dto.UserSubscription;

public interface SimpleSubscriptionCheckService {
    void simpleSubscribe(UserSubscription userSubscription);
}

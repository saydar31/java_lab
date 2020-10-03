package ru.itis.documents.services;

import ru.itis.documents.dto.UserSubscription;

public interface PremiumSubscriptionCheckService {
    void premiumSubscribe(UserSubscription userSubscription);
}

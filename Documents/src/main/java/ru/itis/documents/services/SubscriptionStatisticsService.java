package ru.itis.documents.services;

import ru.itis.documents.dto.UserSubscription;

public interface SubscriptionStatisticsService {
    void makeStatistics(UserSubscription userSubscription);
}

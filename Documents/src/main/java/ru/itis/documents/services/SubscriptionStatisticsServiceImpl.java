package ru.itis.documents.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.SubscriptionType;
import ru.itis.documents.dto.UserSubscription;
@Slf4j
@Component
public class SubscriptionStatisticsServiceImpl implements SubscriptionStatisticsService {
    private int simpleCount;
    private int premiumCount;

    public SubscriptionStatisticsServiceImpl() {
        simpleCount = 0;
        premiumCount = 0;
    }

    @Override
    public void makeStatistics(UserSubscription userSubscription) {
        if (userSubscription.getSubscriptionDto().getSubscriptionType().equals(SubscriptionType.PREMIUM)) {
            premiumCount++;
        } else {
            simpleCount++;
        }
        long sum = premiumCount+simpleCount;
        if ((sum)%10==0){
            log.info("total subs:"+sum+" premium:"+premiumCount+" simple:"+simpleCount);
        }
    }
}

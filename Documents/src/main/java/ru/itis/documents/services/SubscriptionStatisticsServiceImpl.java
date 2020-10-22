package ru.itis.documents.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.SubscriptionDto;
import ru.itis.documents.dto.SubscriptionType;
import ru.itis.documents.dto.UserSubscription;
import ru.itis.documents.model.User;

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
        if (userSubscription.getUser() == null) {
            userSubscription.setUser(User.builder()
                    .firstName("aydar")
                    .lastName("shaydullin")
                    .id(1L)
                    .mail("mail")
                    .build());
        }
        if (userSubscription.getSubscriptionDto() == null) {
            userSubscription.setSubscriptionDto(SubscriptionDto.builder()
                    .monthCount(2)
                    .subscriptionType(SubscriptionType.PREMIUM).build());
        }
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

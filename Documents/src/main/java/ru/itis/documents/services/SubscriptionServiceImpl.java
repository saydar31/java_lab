package ru.itis.documents.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.SubscriptionDto;
import ru.itis.documents.dto.SubscriptionType;
import ru.itis.documents.dto.UserSubscription;

@Component
public class SubscriptionServiceImpl implements SubscriptionService {
    @Qualifier("customRabbitTemplate")
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private UserService userService;

    @Override
    public void subscribe(SubscriptionDto subscriptionDto) {
        UserSubscription subscription = UserSubscription.builder()
                .subscriptionDto(subscriptionDto)
                .user(userService.getCurrentUser())
                .build();
        String key = subscriptionDto.getSubscriptionType().equals(SubscriptionType.PREMIUM) ? "premium.statistics" : "simple.statistics";
        amqpTemplate.convertAndSend("subscription", key, subscription);
    }
}

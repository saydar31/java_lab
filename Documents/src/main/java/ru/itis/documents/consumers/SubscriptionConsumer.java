package ru.itis.documents.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.UserSubscription;
import ru.itis.documents.services.PremiumSubscriptionCheckService;
import ru.itis.documents.services.SimpleSubscriptionCheckService;
import ru.itis.documents.services.SubscriptionStatisticsService;

@Component
public class SubscriptionConsumer {
    @Autowired
    private PremiumSubscriptionCheckService premiumService;
    @Autowired
    private SimpleSubscriptionCheckService simpleService;
    @Autowired
    private SubscriptionStatisticsService statisticsService;

    @RabbitListener(queues = "simple_subscription")
    public void simpleConsume(UserSubscription userSubscription) {
        simpleService.simpleSubscribe(userSubscription);
    }

    @RabbitListener(queues = "premium_subscription")
    public void premiumConsume(UserSubscription userSubscription) {
        premiumService.premiumSubscribe(userSubscription);
    }

    @RabbitListener(queues = "statistics_subscription")
    public void makeStatistics(UserSubscription userSubscription) {
        statisticsService.makeStatistics(userSubscription);
    }
}

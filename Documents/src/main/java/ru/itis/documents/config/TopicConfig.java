package ru.itis.documents.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    @Bean("premiumSubscriptionQueue")
    public Queue premiumSubscriptionQueue() {
        return new Queue("premium_subscription");
    }

    @Bean("simpleSubscriptionQueue")
    public Queue simpleSubscriptionQueue() {
        return new Queue("simple_subscription");
    }

    @Bean("statisticsSubscriptionQueue")
    public Queue statisticsSubscriptionQueue() {
        return new Queue("statistics_subscription");
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("subscription");
    }

    @Bean
    public Binding simpleSubscriptionBinding(@Qualifier("simpleSubscriptionQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("simple.*");
    }

    @Bean
    public Binding premiumSubscriptionBinding(@Qualifier("premiumSubscriptionQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("premium.*");
    }

    @Bean
    public Binding statisticsSubscriptionBinding(@Qualifier("statisticsSubscriptionQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("*.statistics");
    }
}

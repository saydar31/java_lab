package ru.itis.documents.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    @Bean("filmPurchaseStatisticsQueue")
    public Queue logQueue() {
        return new Queue("film_purchase_statistics");
    }

    @Bean("filmPurchaseQueue")
    public Queue pdfQueue() {
        return new Queue("film_purchase");
    }

    @Bean("filmPurchaseFanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("filmPurchase");
    }

    @Bean
    public Binding logBinding(@Qualifier("filmPurchaseStatisticsQueue") Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding pdfBinding(@Qualifier("filmPurchaseQueue") Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}

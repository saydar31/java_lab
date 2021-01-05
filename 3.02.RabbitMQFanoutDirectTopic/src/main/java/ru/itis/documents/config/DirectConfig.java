package ru.itis.documents.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {
    @Bean("shortFilmQueue")
    public Queue shortFilmQueue() {
        return new Queue("short_film_queue");
    }

    @Bean("fullLengthFilmQueue")
    public Queue fullLengthFilmQueue() {
        return new Queue("full_length_film_queue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("film_add");
    }

    @Bean("fullLengthFilmBinding")
    public Binding fullLengthFilmBinding(DirectExchange directExchange, @Qualifier("fullLengthFilmQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("full_length");
    }

    @Bean("shortFilmBinding")
    public Binding shortFilmBinding(DirectExchange directExchange, @Qualifier("shortFilmQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("short");
    }
}

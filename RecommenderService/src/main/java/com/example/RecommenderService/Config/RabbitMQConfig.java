package com.example.RecommenderService.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "event_exchange";
    public static final String QUEUE_NAME = "EventManagementQueue";

    @Bean
    public DirectExchange eventExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue eventQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding eventBinding(DirectExchange eventExchange, Queue eventQueue) {
        return BindingBuilder.bind(eventQueue)
                .to(eventExchange)
                .with("event_key");
    }
}

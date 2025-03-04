package com.ecommerce.produit.service_produit.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.BindingBuilder;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "ecommerce-exchange";
    public static final String QUEUE_NAME = "stock-update-queue";
    public static final String ROUTING_KEY = "stock.update";

    @Bean
    public Queue stockUpdateQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue stockUpdateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(stockUpdateQueue).to(exchange).with(ROUTING_KEY);
    }

}



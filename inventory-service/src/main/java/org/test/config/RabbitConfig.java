package org.test.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


    @Configuration
    public class RabbitConfig {

        // Exchange
        @Bean
        public TopicExchange productExchange() {
            return new TopicExchange("product.exchange");
        }

        // Queue
        @Bean
        public Queue productCreatedQueue() {
            return new Queue("product.created.queue");
        }

        // Binding
        @Bean
        public Binding binding(Queue productCreatedQueue, TopicExchange productExchange) {
            return BindingBuilder
                    .bind(productCreatedQueue)
                    .to(productExchange)
                    .with("product.created");
        }
}

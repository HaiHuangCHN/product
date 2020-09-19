package com.product.config;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
@Configuration
@DependsOn("systemConfig")
public class RabbitMQConfig {

    public static final String DEFAULT_BOOK_QUEUE = "dev.book.register.default.queue";
    public static final String MANUAL_BOOK_QUEUE = "dev.book.register.manual.queue";

    @Bean
    public Queue defaultBookQueue() {
        return new Queue(DEFAULT_BOOK_QUEUE, false);
    }

    @Bean
    public Queue manualBookQueue() {
        return new Queue(MANUAL_BOOK_QUEUE, true);
    }
}
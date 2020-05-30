package com.product.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@TestConfiguration
public class RestTemplateConfigTest {

    /**
     * TODO study RestTemplateBuilder().basicAuthorization("hai", "password")
     * 
     * @return
     */
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(5).getNano());
    }
}

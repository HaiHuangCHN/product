package com.product.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

/**
 * Use static class to inject test configuration
 * 
 * @author Huang, Hai
 * @date Mar 7, 2020 11:55:10 PM
 * @email hai.huang.a@outlook.com
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest2 {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {

            return new RestTemplateBuilder().basicAuthorization("hai", "password").setConnectTimeout(Duration.ofSeconds(5).getNano());
        }

    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void postUserOk() {
        // ...
    }

}

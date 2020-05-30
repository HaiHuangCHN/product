package com.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@DependsOn("systemConfig")
public class RestTemplateConfig {

    @Value("${restclient.connection-timeout}")
    private int connectionTimeout;

    @Value(value = "${restclient.connection-request-timeout}")
    private int connectionRequestTimeout;

    @Value(value = "${restclient.read-timeout}")
    private int readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(connectionTimeout);
        factory.setConnectionRequestTimeout(connectionRequestTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;
    }
}

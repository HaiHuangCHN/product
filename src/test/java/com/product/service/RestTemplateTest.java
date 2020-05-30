package com.product.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.product.config.RestTemplateConfigTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(RestTemplateConfigTest.class)
public class RestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void postUserOk() {
        // ...
        System.out.println("Hello!");
    }

}

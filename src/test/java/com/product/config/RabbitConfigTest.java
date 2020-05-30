//package com.product.config;
//
//import org.mockito.Mockito;
//import org.springframework.amqp.core.Queue;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
///**
// * Mock connection to RabbitMQ, read the following link for more info
// * TODO not work
// * @link https://stackoverflow.com/questions/58673563/mock-connection-to-rabbitmq
// * @author Huang, Hai
// * @date Mar 9, 2020 4:41:23 PM
// * @email hai.huang.a@outlook.com
// *
// */
//@TestConfiguration
//public class RabbitConfigTest {
//
//    @Bean
//    public Queue defaultBookQueue() {
//        return Mockito.mock(Queue.class);
//    }
//
//}
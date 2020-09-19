package com.product.controller;

import org.apache.http.HttpStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.product.config.RabbitMQConfig;
import com.product.costant.Constants;
import com.product.entity.Book;
import com.product.exception.ErrorResponseException;
import com.product.handler.BookHandler;
import com.product.model.response.BookResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = "RabbitMQ Pratice API")
public class RabbitMqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, book); 对应
     * {@link BookHandler#listenerAutoAck}
     * this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, book); 对应
     * {@link BookHandler#listenerManualAck}
     */
    @GetMapping(value = "/books")
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    // swagger need updated...
    public void defaultMessage() {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("一起来学Spring Boot");
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.DEFAULT_BOOK_QUEUE, book);
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.MANUAL_BOOK_QUEUE, book);
    }

}

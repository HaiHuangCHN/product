package com.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.config.RabbitMQConfig;
import com.product.costant.Constants;
import com.product.entity.Book;
import com.product.exception.ErrorResponseException;
import com.product.handler.BookHandler;
import com.product.model.request.AddBookReq;
import com.product.model.response.BookResp;
import com.product.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = "Book API")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add/or/update/book", method = RequestMethod.PUT)
    public ResponseEntity<?> addBook(@RequestBody AddBookReq addBookReq) {
        bookService.saveOrUpdate(addBookReq);
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

    @RequestMapping(value = "/all/books", method = RequestMethod.GET)
    public ResponseEntity<?> allBook() {
        return ResponseEntity.status(HttpStatus.SC_OK).body(bookService.findAll());
    }

    @RequestMapping(value = "/find/book", method = RequestMethod.GET)
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "title", value = "Title of the book", example = "Pepa pig", required = true),
            @ApiImplicitParam(name = "id", value = "ID of the book", example = "1", required = false) })
    public ResponseEntity<?> findBook(@RequestParam(value = "title", required = true) String title, @RequestParam(value = "id", required = false) Integer id)
            throws ErrorResponseException, InterruptedException {
        logger.info("Start fetching book...");
        List<BookResp> bookRespList = bookService.findSmilarTitleBook(title);
        logger.info("Time costed: " + System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.SC_OK).body(bookRespList);
    }

    @RequestMapping(value = "/delete/books", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBook(@RequestParam(value = "title", required = true) String title) {
        bookService.deleteByTitle(title);
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

}

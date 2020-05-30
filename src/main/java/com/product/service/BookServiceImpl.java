package com.product.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.data.DataProvider;
import com.product.entity.Book;
import com.product.exception.ErrorResponseException;
import com.product.model.request.AddBookReq;
import com.product.model.response.BookResp;
import com.product.util.TimeUtils;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private DataProvider dataProvider;

    @Override
    public List<BookResp> findAll() {
        List<BookResp> responseBody = new ArrayList<>();
        dataProvider.getAllBooks().forEach((book) -> {
            BookResp bookResp = new BookResp();
            bookResp.setTitle(book.getTitle());
            bookResp.setAmount(book.getAmount());
            bookResp.setPrice(book.getPrice());
            bookResp.setShortDesc(book.getShortDesc());
            bookResp.setLongDesc(book.getLongDesc());
            responseBody.add(bookResp);
        });
        return responseBody;
    }

    public List<BookResp> findSmilarTitleBook(String title) throws InterruptedException {
        logger.info("Start fetching book...");
        List<BookResp> responseBody = new ArrayList<>();
        dataProvider.getSmilarTitleBook(title).forEach((book) -> {
            BookResp bookResp = new BookResp();
            bookResp.setTitle(book.getTitle());
            bookResp.setAmount(book.getAmount());
            bookResp.setPrice(book.getPrice());
            bookResp.setShortDesc(book.getShortDesc());
            bookResp.setLongDesc(book.getLongDesc());
            responseBody.add(bookResp);
        });
        return responseBody;
    }

    @Override
    public Book findById(Long id) throws ErrorResponseException {
        return dataProvider.getBookByBookId(id);
    }

    @Override
    public Book saveOrUpdate(AddBookReq addBookRequest) {
        Book book = new Book();
        LocalDateTime now = TimeUtils.getUtcTime();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setIsbn(addBookRequest.getIsbn());
        book.setPrice(addBookRequest.getPrice());
        book.setAmount(addBookRequest.getAmount());
        book.setShortDesc(addBookRequest.getShortDesc());
        book.setLongDesc(addBookRequest.getLongDesc());
        book.setCreatedAt(now);
        book.setUpdatedAt(now);
        return dataProvider.addOrUpdateBook(book, addBookRequest.getTitle());
    }

    @Override
    public void deleteByTitle(String title) {
        dataProvider.deleteBookByTitle(title);
    }

}

package com.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.entity.Book;
import com.product.exception.ErrorResponseException;
import com.product.model.request.AddBookReq;
import com.product.model.response.BookResp;

@Service
public interface BookService {

    public List<BookResp> findAll();

    public Book findById(Long id) throws ErrorResponseException;

    public List<BookResp> findSmilarTitleBook(String title) throws InterruptedException;

    public Book saveOrUpdate(AddBookReq addBookRequest);

    public void deleteByTitle(String title);

}

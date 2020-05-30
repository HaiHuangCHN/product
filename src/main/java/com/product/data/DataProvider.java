package com.product.data;

import java.util.List;

import org.springframework.stereotype.Component;

import com.product.entity.Book;
import com.product.entity.Cart;
import com.product.entity.Catalogue;
import com.product.entity.Currency;
import com.product.entity.OrderDetail;
import com.product.entity.User;
import com.product.exception.ErrorResponseException;

@Component
public interface DataProvider {
    /* Book */
    public List<Book> getAllBooks();

    public Book getBookByBookId(Long id) throws ErrorResponseException;

    public List<Book> getSmilarTitleBook(String title) throws InterruptedException;

    public Book addOrUpdateBook(Book book, String title);

    public void deleteBookByTitle(String title);

    /* Currency */
    public Currency getByCurrencyCode(String currencyCode);

    /* User */
    public User addOrSaveUser(Long userId);

    /* Catalogue */
    public List<Catalogue> getCatalogue();

    public List<Catalogue> getCatalogueByCatagory(String catagory);

    /* Cart */
    public Cart addOrUpdateCart(User user, Cart cart);

    /* Order Detail */
    public List<OrderDetail> getOrderDetailByUserId(long userId);

    /* Product Item */
    public List<OrderDetail> addProductItem(long userId);

}
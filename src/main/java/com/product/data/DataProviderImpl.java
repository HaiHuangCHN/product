package com.product.data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.product.config.ScheduleJobConfig;
import com.product.costant.ErrorCodeEnum;
import com.product.entity.Book;
import com.product.entity.Cart;
import com.product.entity.Catalogue;
import com.product.entity.Currency;
import com.product.entity.HousekeepSummary;
import com.product.entity.OrderDetail;
import com.product.entity.User;
import com.product.exception.ErrorResponseException;
import com.product.repository.BookRepository;
import com.product.repository.CartRepository;
import com.product.repository.CatalogueRepository;
import com.product.repository.CurrencyRepository;
import com.product.repository.HousekeepSummaryRepository;
import com.product.repository.OrderDetailRepository;
import com.product.repository.UserRepository;

@Component
public class DataProviderImpl implements DataProvider {

    private static final Logger logger = LoggerFactory.getLogger(DataProviderImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book getBookByBookId(Long id) throws ErrorResponseException {
        return bookRepository.findById(id).orElseThrow(() -> new ErrorResponseException(ErrorCodeEnum.BOOK_NOT_FOUND.getSelfDefinedCode(),
                ErrorCodeEnum.BOOK_NOT_FOUND.getMessage(), ErrorCodeEnum.BOOK_NOT_FOUND.getDetail()));
    }

    @Cacheable("book")
    @Override
    public List<Book> getSmilarTitleBook(String title) throws InterruptedException {
        simulateSlowService();
        return bookRepository.findSmilarTitleBook(title);
    }

    private void simulateSlowService() throws InterruptedException {
        long time = 5000L;
        Thread.sleep(time);
    }

    @Override
    public Book addOrUpdateBook(Book book, String title) {
        return bookRepository.findByTitle(title).map(x -> {// update
            x.setAuthor(book.getAuthor());
            x.setIsbn(book.getTitle());
            x.setPrice(book.getPrice());
            x.setAmount(book.getAmount());
            x.setShortDesc(book.getShortDesc());
            x.setLongDesc(book.getLongDesc());
            return bookRepository.save(x);
        }).orElseGet(() -> {// add
            return bookRepository.save(book);
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deleteBookByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }

    @Override
    public Currency getByCurrencyCode(String currencyCode) {
        return currencyRepository.findByCurrencyCode(currencyCode);
    }

    @Override
    public User addOrSaveUser(Long userId) {
        return userRepository.findByUserId(userId).map(x -> {
            return x;
        }).orElseGet(() -> {// add
            User user = new User();
            return userRepository.save(user);
        });
    }

    @Override
    public List<Catalogue> getCatalogue() {
        return (List<Catalogue>) catalogueRepository.findAll();
    }

    @Override
    public List<Catalogue> getCatalogueByCatagory(String catagory) {
        return catalogueRepository.findByCatagory(Catalogue.CatagoryEnum.valueOf(catagory));
    }

    @Override
    public Cart addOrUpdateCart(User user, Cart cart) {
        return cartRepository.findByUser(user).map(x -> {// update
            return x;
        }).orElseGet(() -> {// add
            return cartRepository.save(cart);
        });
    }

    @Override
    public List<OrderDetail> getOrderDetailByUserId(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<OrderDetail> addProductItem(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

}
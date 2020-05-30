//package com.product.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.product.Application;
//import com.product.config.ScheduleJobConfigTest;
////import com.product.config.RabbitConfigTest;
//import com.product.entity.Book;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = { Application.class }) // specify class to boot
//@ContextConfiguration(classes = { ScheduleJobConfigTest.class/* , RabbitConfigTest.class */})
//public class BookRepositoryTest {
////    @SpyBean // cannot work
//    @MockBean
//    private BookRepository bookRepository;
//
////  @MockBean
////  private DataProviderImpl dataProviderImpl;
//
//    @Test
//    public void exampleTest() {
//        List<Book> bookList = new LinkedList<Book>();
//        Book book = new Book();
//        book.setAmount(100);
//        bookList.add(book);
//
//        given(this.bookRepository.findAll()).willReturn(bookList);
//
//        List<Book> bookListMock = (List<Book>) bookRepository.findAll();
//        assertThat(bookListMock.size()).isEqualTo(1);
//
//        verify(bookRepository, times(1)).findAll();
//    }
//}

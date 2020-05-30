//package com.product.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.product.entity.Book;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class BookRepositoryTest2 {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Test
//    public void whenFindByName_thenReturnEmployee() {
//        // given
//        Book alex = new Book();
//        alex.setTitle("title");
//        entityManager.persist(alex);
//        entityManager.flush();
//
//        // when
//        List<Book> found = bookRepository.findSmilarTitleBook(alex.getTitle());
//
//        // then
//        assertThat(found.get(0).getTitle()).isEqualTo(alex.getTitle());
//    }
//
//}

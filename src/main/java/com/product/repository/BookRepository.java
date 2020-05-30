package com.product.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Native Query
     * 
     * @param title
     * @return
     */
    @Query(value = "select * from book where title like CONCAT('%', :title, '%')", nativeQuery = true)
    public List<Book> findSmilarTitleBook(@Param("title") String title);

    /**
     * Derive Query
     * 
     * @param title
     * @return
     */
    public List<Book> findByTitleLike(String title);

    public Optional<Book> findByTitle(String title);

    public Optional<Book> deleteByTitle(String title);

    public List<Book> deleteByUpdatedAtBefore(LocalDateTime updatedAt);
}
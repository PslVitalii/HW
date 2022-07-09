package com.epam.spring.persistence;

import com.epam.spring.model.entity.book.Author;
import com.epam.spring.model.entity.book.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("from Book where publicationDate = ?1")
    List<Book> findByPublicationDate(LocalDate publicationDate);

    List<Book> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Book> findByAuthor(Author author, Pageable pageable);
}

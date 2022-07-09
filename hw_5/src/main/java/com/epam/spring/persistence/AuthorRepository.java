package com.epam.spring.persistence;

import com.epam.spring.model.entity.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, CustomAuthorRepository {
    List<Author> findByBirthDateAndFullNameContainingIgnoreCase(LocalDate birthDate, String fullName);
}

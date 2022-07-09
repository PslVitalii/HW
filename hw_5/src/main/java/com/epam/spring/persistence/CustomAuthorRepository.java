package com.epam.spring.persistence;

import com.epam.spring.model.entity.book.Author;

import java.time.LocalDate;
import java.util.List;

public interface CustomAuthorRepository {
    List<Author> findByBirthDate(LocalDate birthDate);
    List<Author> findByFullNameLike(String fullName);
}

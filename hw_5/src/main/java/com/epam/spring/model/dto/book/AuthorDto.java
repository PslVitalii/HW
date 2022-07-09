package com.epam.spring.model.dto.book;

import com.epam.spring.model.entity.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class AuthorDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String fullName;
    private LocalDate birthDate;

    @JsonIgnoreProperties("author")
    private Set<Book> books = new HashSet<>();
}

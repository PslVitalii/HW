package com.epam.spring.model.dto.book;

import com.epam.spring.model.entity.book.Author;
import com.epam.spring.model.entity.book.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class BookDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String name;
    private LocalDate publicationDate;
    private int numberOfPages;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long authorId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnoreProperties("books")
    private Author author;

    private Set<Genre> genres = new HashSet<>();
}

package com.epam.spring.model.entity.book;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private LocalDate publicationDate;
    private int numberOfPages;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.SELECT)
    private Author author;

    @ManyToMany
    private Set<Genre> genres = new HashSet<>();
}

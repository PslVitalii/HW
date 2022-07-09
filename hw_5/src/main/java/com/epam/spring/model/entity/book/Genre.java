package com.epam.spring.model.entity.book;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String genre;
}

package com.epam.spring.persistence;

import com.epam.spring.model.entity.book.Genre;

import java.util.List;
import java.util.Optional;

public interface CustomGenreRepository {
    void deleteGenre(long id);
    void updateGenre(Genre genre);

    Optional<Genre> findGenre(long id);
    Optional<Genre> findGenre(String genre);

    List<Genre> findAllGenres();
}

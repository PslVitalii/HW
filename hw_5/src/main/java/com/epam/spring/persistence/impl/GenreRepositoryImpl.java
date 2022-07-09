package com.epam.spring.persistence.impl;

import com.epam.spring.model.entity.book.Genre;
import com.epam.spring.persistence.CustomGenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GenreRepositoryImpl implements CustomGenreRepository {
    private final JdbcOperations jdbcOperations;

    private static final String DELETE_GENRE_BY_ID = "delete from genre where id = ?";

    private static final String UPDATE_GENRE = "update genre set genre = ? where id = ?";
    private static final String SELECT_BY_ID = "select * from genre where id = ?";
    private static final String SELECT_BY_GENRE = "select * from genre where genre = ?";
    private static final String SELECT_ALL = "select * from genre";

    @Override
    public void deleteGenre(long id) {
        jdbcOperations.update(DELETE_GENRE_BY_ID, id);
    }

    @Override
    public void updateGenre(Genre genre){
        jdbcOperations.update(UPDATE_GENRE, genre.getGenre(), genre.getId());
    }

    @Override
    public Optional<Genre> findGenre(long id) {
        Genre possibleGenre = jdbcOperations.queryForObject(SELECT_BY_ID, this::mapGenre, id);
        return Optional.ofNullable(possibleGenre);
    }

    @Override
    public Optional<Genre> findGenre(String genre) {
        Genre possibleGenre = jdbcOperations.queryForObject(SELECT_BY_GENRE, this::mapGenre, genre);
        return Optional.ofNullable(possibleGenre);
    }

    @Override
    public List<Genre> findAllGenres() {
        return jdbcOperations.query(SELECT_ALL, this::mapGenre);
    }

    private Genre mapGenre(ResultSet resultSet, int row) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("id"));
        genre.setGenre(resultSet.getString("genre"));

        return genre;
    }
}

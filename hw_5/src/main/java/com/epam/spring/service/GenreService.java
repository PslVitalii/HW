package com.epam.spring.service;

import com.epam.spring.exception.EntityAlreadyExistsException;
import com.epam.spring.exception.EntityNotFoundException;
import com.epam.spring.model.dto.book.GenreDto;
import com.epam.spring.model.entity.book.Author;
import com.epam.spring.model.entity.book.Genre;
import com.epam.spring.persistence.GenreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class GenreService {
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;

    public GenreDto saveGenre(GenreDto genreDto) {
        log.debug("Save genre: {}", genreDto);
        Optional<Genre> possibleGenre = genreRepository.findGenre(genreDto.getGenre());

        if (possibleGenre.isPresent()){
            log.warn("Genre already exists: {}", genreDto.getGenre());
            throw new EntityAlreadyExistsException(genreDto.getGenre(), Genre.class);
        }

        Genre genre = modelMapper.map(genreDto, Genre.class);
        genreRepository.save(genre);

        return mapGenreToGenreDto(genre);
    }

    public void deleteGenre(long id){
        log.debug("Delete genre by id {}", id);
        genreRepository.deleteGenre(id);
    }

    public GenreDto updateGenre(long id, GenreDto genreDto){
        log.debug("Update genre with id {}: {}", id, genreDto);

        genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Author.class));

        Genre genre = modelMapper.map(genreDto, Genre.class);
        genre.setId(id);

        genreRepository.updateGenre(genre);

        return mapGenreToGenreDto(genre);
    }

    @Transactional(readOnly = true)
    public GenreDto getGenre(long id){
        log.debug("Get genre by id {}", id);

        Genre genre = genreRepository.findGenre(id).orElseThrow(() -> new EntityNotFoundException(id, Genre.class));

        return mapGenreToGenreDto(genre);
    }

    @Transactional(readOnly = true)
    public GenreDto getGenre(String genreName){
        log.debug("Get genre by genre name: '{}'", genreName);

        Genre genre = genreRepository.findGenre(genreName).orElseThrow(() -> new EntityNotFoundException(genreName, Genre.class));
        return mapGenreToGenreDto(genre);
    }

    @Transactional(readOnly = true)
    public List<GenreDto> getAll(){
        log.debug("Get all genres");

        return genreRepository.findAllGenres()
                .stream().map(this::mapGenreToGenreDto)
                .collect(Collectors.toList());
    }

    private GenreDto mapGenreToGenreDto(Genre genre){
        return modelMapper.map(genre, GenreDto.class);
    }
}

package com.epam.spring.homework3.service;

import com.epam.spring.homework3.exceptions.EntityNotFoundException;
import com.epam.spring.homework3.model.dto.MovieDto;
import com.epam.spring.homework3.model.entity.Movie;
import com.epam.spring.homework3.persistence.MovieRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class MovieService {
    public final ModelMapper modelMapper;
    public final MovieRepository movieRepository;

    public MovieDto saveMovie(MovieDto movieDto){
        Movie movie = modelMapper.map(movieDto, Movie.class);
        movieRepository.save(movie);

        return mapMovieToMovieDto(movie);
    }

    public void deleteMovie(long id){
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Movie.class));
        movieRepository.delete(movie);
    }

    public void updateMovie(long id, MovieDto movieDto){
        movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Movie.class));

        Movie movie = modelMapper.map(movieDto, Movie.class);
        movie.setId(id);

        movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public MovieDto getMovie(long id){
        return movieRepository.findById(id)
                .map(this::mapMovieToMovieDto)
                .orElseThrow(() -> new EntityNotFoundException(id, Movie.class));
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getMoviesByName(String name){
        return movieRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::mapMovieToMovieDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies(){
        return movieRepository.findAll()
                .stream().map(this::mapMovieToMovieDto)
                .collect(Collectors.toList());
    }

    public MovieDto mapMovieToMovieDto(Movie movie){
        return modelMapper.map(movie, MovieDto.class);
    }
}

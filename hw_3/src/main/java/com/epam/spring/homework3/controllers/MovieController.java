package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.model.dto.MovieDto;
import com.epam.spring.homework3.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public MovieDto saveMovie(@Valid @RequestBody MovieDto movie) {
        movie = movieService.saveMovie(movie);
        return movie;
    }

    @DeleteMapping("{id}")
    public void deleteMovie(@PathVariable long id){
        movieService.deleteMovie(id);
    }

    @PutMapping("{id}")
    public void updateMovie(@PathVariable long id, @Valid @RequestBody MovieDto movieDto){
        movieService.updateMovie(id, movieDto);
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable long id){
        return movieService.getMovie(id);
    }

    @GetMapping
    public List<MovieDto> getMovies(@RequestParam(name = "name", required = false) String name){
        if(name != null) {
            return movieService.getMoviesByName(name);
        }

        return movieService.getAllMovies();
    }
}


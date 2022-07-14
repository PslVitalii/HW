package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.model.dto.MovieDto;
import com.epam.spring.homework3.service.RestTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest-template")
@AllArgsConstructor
public class RestTemplateController {
    private RestTemplateService restTemplateService;

    @PostMapping
    public ResponseEntity<MovieDto> saveMovie(@RequestBody MovieDto movieDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken) {
        return restTemplateService.saveMovie(movieDto, authorizationToken);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken) {
        return restTemplateService.deleteMovie(id, authorizationToken);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable long id) {
        return restTemplateService.getMovieById(id);
    }

    @GetMapping
    public List<MovieDto> getMovies() {
        return restTemplateService.getMovies();
    }
}

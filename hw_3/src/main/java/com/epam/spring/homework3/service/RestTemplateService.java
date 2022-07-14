package com.epam.spring.homework3.service;

import com.epam.spring.homework3.model.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RestTemplateService {
    private final String MOVIE_API = "http://localhost:8080/api/movies";
    private RestTemplate restTemplate;

    public ResponseEntity<MovieDto> saveMovie(MovieDto movieDto, String jwt) {
        log.debug("Save movie: {}", movieDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);

        HttpEntity<MovieDto> httpEntity = new HttpEntity<>(movieDto, httpHeaders);

        return restTemplate.postForEntity(MOVIE_API, httpEntity, MovieDto.class);
    }

    public ResponseEntity<String> deleteMovie(long id, String jwt) {
        log.debug("Delete movie by id: {}", id);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);

        HttpEntity<Object> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(MOVIE_API + "/{id}", HttpMethod.DELETE, request, String.class, id);
    }

    public ResponseEntity<MovieDto> getMovieById(long id) {
        log.debug("Get movie by id: {}", id);

        return restTemplate.getForEntity(MOVIE_API + "/{id}", MovieDto.class, id);
    }

    public List<MovieDto> getMovies() {
        log.debug("Get all movies");

        MovieDto[] responseArray = restTemplate.getForObject(MOVIE_API, MovieDto[].class);

        if (responseArray == null) {
            return new ArrayList<>();
        }

        return List.of(responseArray);
    }
}

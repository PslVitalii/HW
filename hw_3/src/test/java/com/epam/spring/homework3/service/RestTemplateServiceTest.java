package com.epam.spring.homework3.service;

import com.epam.spring.homework3.model.dto.MovieDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestTemplateServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    RestTemplateService restTemplateService;

    @Captor
    private ArgumentCaptor<HttpEntity<MovieDto>> captor;


    private final String API = "http://localhost:8080/api/movies";

    @Test
    void testSaveMovie() {
        // given
        String jwt = "jwt_token";
        MovieDto movieDto = getMovieDto("test");
        ResponseEntity<MovieDto> response = ResponseEntity.ok(movieDto);

        // when
        when(restTemplate.postForEntity(eq(API), any(), eq(MovieDto.class))).thenReturn(response);
        ResponseEntity<MovieDto> result = restTemplateService.saveMovie(movieDto, jwt);

        // then
        verify(restTemplate).postForEntity(eq(API), captor.capture(), eq(MovieDto.class));

        HttpEntity<MovieDto> httpEntity = captor.getValue();
        assertThat(httpEntity.getBody(), equalTo(movieDto));

        HttpHeaders headers = httpEntity.getHeaders();
        System.out.println(headers);
        assertThat(headers.get(HttpHeaders.AUTHORIZATION), Matchers.contains("Bearer " + jwt));

        assertThat(result.getBody(), equalTo(movieDto));
    }

    @Test
    void testDeleteMovie() {
        // given
        long id = 1;
        String jwt = "jwt_token";
        ResponseEntity<String> response = ResponseEntity.ok().build();

        // when
        when(restTemplate.exchange(eq(API + "/{id}"), eq(HttpMethod.DELETE), ArgumentMatchers.any(HttpEntity.class), eq(String.class), eq(id))).thenReturn(response);
        ResponseEntity<String> result = restTemplateService.deleteMovie(id, jwt);

        // then
        verify(restTemplate).exchange(eq(API + "/{id}"), eq(HttpMethod.DELETE), ArgumentMatchers.any(HttpEntity.class), eq(String.class), eq(id));
        assertThat(result, equalTo(response));
    }

    @Test
    void tesGetMovieById() {
        // given
        long id = 1;
        ResponseEntity<MovieDto> response = ResponseEntity.ok(getMovieDto("test"));

        // when
        when(restTemplate.getForEntity(API + "/{id}", MovieDto.class, id)).thenReturn(response);
        ResponseEntity<MovieDto> result = restTemplateService.getMovieById(id);

        // then
        verify(restTemplate).getForEntity(API + "/{id}", MovieDto.class, id);
        assertThat(result, equalTo(response));
    }

    @Test
    void testGetMovies() {
        // given
        MovieDto[] moviesDtoArray = {getMovieDto("test"), getMovieDto("test1")};

        // when
        when(restTemplate.getForObject(API, MovieDto[].class)).thenReturn(moviesDtoArray);
        List<MovieDto> result = restTemplateService.getMovies();

        // then
        verify(restTemplate).getForObject(API, MovieDto[].class);
        assertThat(result, containsInAnyOrder(moviesDtoArray));
    }

    @Test
    void testGetMoviesRestTemplateReturnsNull() {
        // when
        when(restTemplate.getForObject(API, MovieDto[].class)).thenReturn(null);
        List<MovieDto> result = restTemplateService.getMovies();

        // then
        verify(restTemplate).getForObject(API, MovieDto[].class);
        assertThat(result, hasSize(0));
    }

    private MovieDto getMovieDto(String name) {
        MovieDto movieDto = new MovieDto();
        movieDto.setName(name);

        return movieDto;
    }
}
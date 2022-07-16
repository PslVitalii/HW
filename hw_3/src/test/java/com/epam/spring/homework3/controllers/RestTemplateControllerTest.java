package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.config.security.jwt.JwtTokenVerifierFilter;
import com.epam.spring.homework3.model.dto.MovieDto;
import com.epam.spring.homework3.service.RestTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.Utils.objectToJson;

@WebMvcTest(
        controllers = RestTemplateController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtTokenVerifierFilter.class)
        },
        excludeAutoConfiguration = {UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class}
)
@ActiveProfiles("test")
class RestTemplateControllerTest {

    @MockBean
    RestTemplateService restTemplateService;

    @Autowired
    MockMvc mvc;

    private static final String REST_TEMPLATE_API = "/api/rest-template";

    @Test
    void testSaveMovie() throws Exception {
        // given
        String name = "test";
        MovieDto movieDto = getMovieDto(name);
        String jwt = "Bearer jkfpqtq";
        ResponseEntity<MovieDto> response = ResponseEntity.ok(movieDto);

        when(restTemplateService.saveMovie(movieDto, jwt)).thenReturn(response);

        mvc.perform(
                        post(REST_TEMPLATE_API)
                                .content(objectToJson(movieDto))
                                .header(HttpHeaders.AUTHORIZATION, jwt)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)));

        verify(restTemplateService).saveMovie(movieDto, jwt);
    }

    @Test
    void testDeleteMovie() throws Exception {
        // given
        long id = 1;
        String jwt = "Bearer jkfpqtq";
        ResponseEntity<String> response = ResponseEntity.ok().build();

        when(restTemplateService.deleteMovie(id, jwt)).thenReturn(response);

        mvc.perform(
                        delete(REST_TEMPLATE_API + "/" + id)
                                .header(HttpHeaders.AUTHORIZATION, jwt))
                .andExpect(status().isOk());

        verify(restTemplateService).deleteMovie(id, jwt);
    }

    @Test
    void testDeleteMovieFails() throws Exception {
        // given
        long id = 1;
        String jwt = "Bearer jkfpqtq";
        ResponseEntity<String> response = ResponseEntity.notFound().build();

        when(restTemplateService.deleteMovie(id, jwt)).thenReturn(response);

        mvc.perform(
                        delete(REST_TEMPLATE_API + "/" + id)
                                .header(HttpHeaders.AUTHORIZATION, jwt))
                .andExpect(status().isNotFound());

        verify(restTemplateService).deleteMovie(id, jwt);
    }

    @Test
    void getMovieById() throws Exception {
        // given
        long id = 1;
        String name = "test";
        MovieDto movieDto = getMovieDto(name);
        ResponseEntity<MovieDto> response = ResponseEntity.ok(movieDto);

        when(restTemplateService.getMovieById(id)).thenReturn(response);

        mvc.perform(
                        get(REST_TEMPLATE_API + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)));

        verify(restTemplateService).getMovieById(id);
    }

    @Test
    void getMovies() throws Exception {
        // given
        List<MovieDto> movieDtoList = List.of(
                getMovieDto("test"),
                getMovieDto("test1")
        );

        when(restTemplateService.getMovies()).thenReturn(movieDtoList);

        mvc.perform(
                        get(REST_TEMPLATE_API))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(movieDtoList.size())));

        verify(restTemplateService).getMovies();
    }

    private MovieDto getMovieDto(String name) {
        MovieDto movieDto = new MovieDto();
        movieDto.setName(name);

        return movieDto;
    }
}
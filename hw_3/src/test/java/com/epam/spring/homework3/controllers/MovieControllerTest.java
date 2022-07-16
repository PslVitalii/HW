package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.exceptions.EntityNotFoundException;
import com.epam.spring.homework3.model.dto.MovieDto;
import com.epam.spring.homework3.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.Utils.objectToJson;

@WebMvcTest(
        controllers = MovieController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class),
        excludeAutoConfiguration = {UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class}
)
@ActiveProfiles("test")
class MovieControllerTest {

    @MockBean
    MovieService movieService;

    @Autowired
    MockMvc mvc;

    private static final String MOVIES_API = "/api/movies";

    @Test
    void testSaveMovie() throws Exception {
        // given
        long id = 1;
        String name = "test";
        MovieDto requestMovieDto = getMovieDto(0, name);
        MovieDto responseMovieDto = getMovieDto(id, name);

        when(movieService.saveMovie(requestMovieDto)).thenReturn(responseMovieDto);

        mvc.perform(post(MOVIES_API).content(objectToJson(requestMovieDto)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(not(0)))).andExpect(jsonPath("$.name", is(name)));

        verify(movieService).saveMovie(requestMovieDto);
    }

    @Test
    void testSaveMovieReturnsBadRequestForInvalidArguments() throws Exception {
        // given
        long id = 1;
        MovieDto requestMovieDto = getMovieDto(0, null);
        requestMovieDto.setDuration(0L);
        requestMovieDto.setOverview("");
        requestMovieDto.setTrailerUrl("www.youtube/watch?v=1234");

        mvc.perform(post(MOVIES_API).content(objectToJson(requestMovieDto)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteMovie() throws Exception {
        // given
        long id = 1;

        mvc.perform(delete(MOVIES_API + "/" + id)).andExpect(status().isOk());

        verify(movieService).deleteMovie(id);
    }

    @Test
    void testUpdateMovieReturnsBadRequestForInvalidArguments() throws Exception {
        // given
        long id = 1;
        MovieDto requestMovieDto = getMovieDto(0, null);
        requestMovieDto.setDuration(0L);
        requestMovieDto.setOverview("");
        requestMovieDto.setTrailerUrl("www.youtube/watch?v=1234");

        mvc.perform(put(MOVIES_API + "/" + id).content(objectToJson(requestMovieDto)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateMovie() throws Exception {
        // given
        long id = 1;
        String name = "test";
        MovieDto requestMovieDto = getMovieDto(id, name);

        mvc.perform(put(MOVIES_API + "/" + id).content(objectToJson(requestMovieDto)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(movieService).updateMovie(id, requestMovieDto);
    }

    @Test
    void testGetMovieById() throws Exception {
        // given
        int id = 1;
        String name = "test";
        MovieDto movieDto = getMovieDto(id, name);

        when(movieService.getMovie(id)).thenReturn(movieDto);

        mvc.perform(get(MOVIES_API + "/" + id)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id", is(id))).andExpect(jsonPath("$.name", is(name)));

        verify(movieService).getMovie(id);
    }

    @Test
    void testGetMovieByIdReturnsNotFound() throws Exception {
        // given
        int id = 1;

        when(movieService.getMovie(id)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get(MOVIES_API + "/" + id)).andExpect(status().isNotFound());

        verify(movieService).getMovie(id);
    }

    @Test
    void testGetMovies() throws Exception {
        // given
        List<MovieDto> moviesList = List.of(getMovieDto(1, "test"), getMovieDto(2, "test1"));

        when(movieService.getAllMovies()).thenReturn(moviesList);

        mvc.perform(get(MOVIES_API)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].name", is(moviesList.get(0).getName())));

        verify(movieService).getAllMovies();
    }

    @Test
    void testGetMoviesByName() throws Exception {
        // given
        String movieName = "test";
        List<MovieDto> moviesList = List.of(getMovieDto(1, "test"), getMovieDto(2, "test1"));

        when(movieService.getMoviesByName(movieName)).thenReturn(moviesList);

        mvc.perform(get(MOVIES_API).param("name", movieName)).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].name", is(moviesList.get(0).getName())));

        verify(movieService).getMoviesByName(movieName);
    }

    @Test
    void testAddGenreToMovie() throws Exception {
        // given
        long movieId = 1;
        long genreId = 2;

        String api = String.format("%s/%d/%s/%d", MOVIES_API, movieId, "genres", genreId);

        mvc.perform(put(api)).andExpect(status().isOk());

        verify(movieService).addGenreToMovie(movieId, genreId);
    }

    @Test
    void testRemoveGenreFromMovie() throws Exception {
        // given
        long movieId = 1;
        long genreId = 2;

        String api = String.format("%s/%d/%s/%d", MOVIES_API, movieId, "genres", genreId);

        mvc.perform(delete(api)).andExpect(status().isOk());

        verify(movieService).removeGenreFromMovie(movieId, genreId);
    }

    private MovieDto getMovieDto(long id, String name) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(id);
        movieDto.setName(name);
        movieDto.setOverview("Some text just to pass validation");
        movieDto.setDuration(123L);
        movieDto.setReleaseDate(LocalDate.of(2021, 11, 4));
        movieDto.setTrailerUrl("https://www.youtube.com/watch?v=fN9hm7k9fns");

        return movieDto;
    }
}
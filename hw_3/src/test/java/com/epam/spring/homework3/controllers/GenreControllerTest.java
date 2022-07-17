package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.model.dto.GenreDto;
import com.epam.spring.homework3.service.GenreService;
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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.Utils.objectToJson;

@WebMvcTest(
        controllers = GenreController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class),
        excludeAutoConfiguration = {UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class}
)
@ActiveProfiles("test")
class GenreControllerTest {

    @MockBean
    GenreService genreService;

    @Autowired
    private MockMvc mvc;

    private static final String GENRES_API = "/api/genres";

    @Test
    void testSaveGenre() throws Exception {
        // given
        String genreName = "test";
        GenreDto requestGenreDto = getGenreDto(0, genreName);
        GenreDto responseGenreDto = getGenreDto(1, genreName);

        when(genreService.saveGenre(any(GenreDto.class))).thenReturn(responseGenreDto);

        mvc.perform(
                        post(GENRES_API)
                                .content(objectToJson(requestGenreDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(not(0))))
                .andExpect(jsonPath("$.genre", is(genreName)));

        verify(genreService).saveGenre(requestGenreDto);
    }

    @Test
    void testSaveGenreReturnsBadRequestForInvalidGenreName() throws Exception {
        // given
        GenreDto requestGenreDto = getGenreDto(0, null);

        mvc.perform(
                        post(GENRES_API)
                                .content(objectToJson(requestGenreDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[0].field", is("genre")));
    }

    @Test
    void testDeleteGenre() throws Exception {
        // given
        long id = 1;

        mvc.perform(
                        delete(GENRES_API + "/" + id))
                .andExpect(status().isOk());

        verify(genreService).deleteGenre(id);
    }

    @Test
    void testUpdateGenre() throws Exception {
        // given
        long id = 1;
        String genreName = "test";
        GenreDto requestGenreDto = getGenreDto(id, genreName);

        mvc.perform(
                        put(GENRES_API + "/" + id)
                                .content(objectToJson(requestGenreDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(genreService).updateGenre(id, requestGenreDto);
    }

    @Test
    void testUpdateGenreReturnsBadRequestForInvalidGenreName() throws Exception {
        // given
        long id = 1;
        GenreDto requestGenreDto = getGenreDto(id, null);

        mvc.perform(
                        put(GENRES_API + "/" + id)
                                .content(objectToJson(requestGenreDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[0].field", is("genre")));
    }

    @Test
    void testGetGenreById() throws Exception {
        // given
        int id = 1;
        String genreName = "test";
        GenreDto genreDto = getGenreDto(id, genreName);

        when(genreService.getGenre(id)).thenReturn(genreDto);

        mvc.perform(
                        get(GENRES_API + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.genre", is(genreName)));

        verify(genreService).getGenre(id);
    }

    @Test
    void testGetAllGenres() throws Exception {
        // given
        List<GenreDto> genreList = List.of(getGenreDto(1, "test"), getGenreDto(2, "test1"));

        when(genreService.getAllGenres()).thenReturn(genreList);

        mvc.perform(
                        get(GENRES_API)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].genre", is(genreList.get(0).getGenre())));

        verify(genreService).getAllGenres();
    }

    private GenreDto getGenreDto(long id, String genreName) {
        GenreDto genre = new GenreDto();
        genre.setId(id);
        genre.setGenre(genreName);

        return genre;
    }
}
package application;

import com.epam.spring.homework3.Application;
import com.epam.spring.homework3.model.dto.AuthenticationRequestDto;
import com.epam.spring.homework3.model.dto.GenreDto;
import com.epam.spring.homework3.model.dto.MovieDto;
import com.epam.spring.homework3.model.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@Profile("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String API;

    private static String jwtToken;

    @BeforeAll
    void init() {
        API = "http://localhost:" + port;

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setEmail("admin@mail.com");
        authenticationRequestDto.setPassword("password");

        ResponseEntity<UserDto> response = restTemplate.postForEntity(
                API + "/api/auth/login",
                authenticationRequestDto,
                UserDto.class
        );

        jwtToken = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }

    @Test
    void testSignUp() {
        // given
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("test@mail.com");
        userDto.setPassword("password");

        // when
        ResponseEntity<UserDto> response = restTemplate.postForEntity(API + "/api/auth/signup", userDto, UserDto.class);

        // then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getId(), is(not(0)));
        assertThat(response.getBody().getEmail(), is(userDto.getEmail()));
    }

    @Test
    void testSaveMovie() {
        // given
        MovieDto movieDto = new MovieDto();
        movieDto.setName("Test");
        movieDto.setOverview("Some text just to pass validation");
        movieDto.setDuration(123L);
        movieDto.setReleaseDate(LocalDate.parse("2021-12-13"));
        movieDto.setTrailerUrl("https://www.youtube.com/watch?v=xlVX7dXLS64&ab_channel=Reducible");
        movieDto.setDirectors(Set.of("test"));
        movieDto.setActors(Set.of("test", "test1", "test2"));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        HttpEntity<MovieDto> httpEntity = new HttpEntity<>(movieDto, headers);

        // when
        ResponseEntity<MovieDto> response = restTemplate.postForEntity(API + "/api/movies", httpEntity, MovieDto.class);

        // then
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getId(), is(not(0)));
        assertThat(response.getBody().getName(), is(movieDto.getName()));
    }

    @Test
    void testGetMovies(){

    }

    @Test
    void testSaveGenre(){
        // given
        GenreDto genreDto = new GenreDto();
        genreDto.setGenre("Test");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        HttpEntity<GenreDto> httpEntity = new HttpEntity<>(genreDto, headers);

        // when
        ResponseEntity<GenreDto> response = restTemplate.postForEntity(API + "/api/genres", httpEntity, GenreDto.class);

        // then
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getId(), is(not(0)));
        assertThat(response.getBody().getGenre(), is(genreDto.getGenre()));
    }
}

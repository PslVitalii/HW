package com.epam.spring.homework3.model.dto;

import com.epam.spring.homework3.model.entity.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private Long id;

    @NotBlank
    private String genre;

    @JsonIgnoreProperties("genres")
    private Set<Movie> movies = new HashSet<>();
}

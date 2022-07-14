package com.epam.spring.homework3.model.dto;

import com.epam.spring.homework3.model.entity.Genre;
import com.epam.spring.homework3.validation.annotations.YoutubeVideoUrlValidation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@NotBlank(message = "{movie.name.not-blank}")
	private String name;

	@Size(min = 24, max = 1024, message = "{movie.overview.not-empty}")
	private String overview;

	@Min(value = 1, message = "{movie.duration}")
	private Long duration;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate releaseDate;

	@YoutubeVideoUrlValidation(message = "{movie.trailer-url}")
	private String trailerUrl;

	private String posterImage;
	private Set<String> previewImages;

	@JsonIgnoreProperties("{movies}")
	private Set<Genre> genres = new HashSet<>();

	private Set<String> directors = new HashSet<>();
	private Set<String> actors = new HashSet<>();
}

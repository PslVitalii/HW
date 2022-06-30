package com.epam.spring.homework3.controllers;

import com.epam.spring.homework3.model.dto.GenreDto;
import com.epam.spring.homework3.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GenreDto saveGenre(@Valid @RequestBody GenreDto genre){
        return genreService.saveGenre(genre);
    }

    @DeleteMapping("{id}")
    public void deleteGenre(@PathVariable long id){
        genreService.deleteGenre(id);
    }

    @PutMapping("{id}")
    public void updateGenre(@PathVariable long id, @Valid @RequestBody GenreDto genre){
        genreService.updateGenre(id, genre);
    }

    @GetMapping
    public List<GenreDto> getAllGenres(){
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreDto getGenre(@PathVariable long id){
        return genreService.getGenre(id);
    }
}

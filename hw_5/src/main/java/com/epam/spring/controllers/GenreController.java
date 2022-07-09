package com.epam.spring.controllers;

import com.epam.spring.model.dto.book.GenreDto;
import com.epam.spring.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto saveGenre(@RequestBody GenreDto genre) {
        return genreService.saveGenre(genre);
    }

    @DeleteMapping("{id}")
    public void deleteGenre(@PathVariable long id) {
        genreService.deleteGenre(id);
    }

    @PutMapping("{id}")
    public GenreDto updateGenre(@PathVariable long id, @RequestBody GenreDto genre){
        return genreService.updateGenre(id, genre);
    }

    @GetMapping("{id}")
    public GenreDto getGenre(@PathVariable long id){
        return genreService.getGenre(id);
    }

    @GetMapping
    public List<GenreDto> getAll() {
        return genreService.getAll();
    }
}

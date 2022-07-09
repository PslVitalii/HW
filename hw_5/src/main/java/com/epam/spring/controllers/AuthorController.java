package com.epam.spring.controllers;


import com.epam.spring.model.dto.book.AuthorDto;
import com.epam.spring.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto saveAuthor(@RequestBody AuthorDto author) {
        return authorService.saveAuthor(author);
    }

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable long id) {
        authorService.deleteAuthor(id);
    }

    @PutMapping("{id}")
    public AuthorDto updateAuthor(@PathVariable long id, AuthorDto authorDto) {
        return authorService.updateAuthor(id, authorDto);
    }

    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping
    public List<AuthorDto> getAll(@RequestParam(required = false) String fullName, @RequestParam(required = false) LocalDate birthDate) {
        if (fullName != null && birthDate != null){
            return authorService.getAuthor(birthDate, fullName);
        } else if (fullName != null) {
            return authorService.getByFullName(fullName);
        } else if (birthDate != null) {
            return authorService.getByBirthDate(birthDate);
        }

        return authorService.getAll();
    }
}

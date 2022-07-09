package com.epam.spring.controllers;

import com.epam.spring.model.dto.book.BookDto;
import com.epam.spring.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public BookDto saveBook(@RequestBody BookDto bookDto) {
        return bookService.saveBook(bookDto);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }

    @PutMapping("{id}")
    public BookDto updateBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    @GetMapping("{id}")
    public BookDto getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @GetMapping(params = "publicationDate")
    public List<BookDto> getBooksByPublicationDate(@RequestParam LocalDate publicationDate) {
        return bookService.getBooksByPublicationDate(publicationDate);
    }

    @GetMapping(params = "name")
    public List<BookDto> getBooksByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return bookService.getBooksByName(name, page, size);
    }

    @GetMapping(params = "authorId")
    public List<BookDto> getBooksByAuthor(
            @RequestParam long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return bookService.getBooksByAuthor(authorId, page, size);
    }

    @GetMapping
    public List<BookDto> getBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return bookService.getAll(page, size);
    }
}

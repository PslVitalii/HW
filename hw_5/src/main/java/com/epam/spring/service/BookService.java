package com.epam.spring.service;

import com.epam.spring.exception.EntityNotFoundException;
import com.epam.spring.model.dto.book.BookDto;
import com.epam.spring.model.entity.book.Author;
import com.epam.spring.model.entity.book.Book;
import com.epam.spring.persistence.AuthorRepository;
import com.epam.spring.persistence.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class BookService {
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookDto saveBook(BookDto bookDto){
        log.debug("Save book: {}", bookDto);
        long authorId = bookDto.getAuthorId();

        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException(authorId, Author.class));
        log.debug("Author with id {}: {}", authorId, author);

        Book book = modelMapper.map(bookDto, Book.class);
        book.setAuthor(author);
        bookRepository.save(book);

        return mapBookToBookDto(book);
    }

    public void deleteBook(long id){
        log.debug("Delete book by id: {}", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Author.class));

        bookRepository.delete(book);
    }

    public BookDto updateBook(long id, BookDto bookDto){
        log.debug("Update book with id {}: {}", id, bookDto);
        bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Author.class));

        Book book = modelMapper.map(bookDto, Book.class);
        book.setId(id);

        bookRepository.save(book);

        return mapBookToBookDto(book);
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(long id) {
        log.debug("Get book by id {}", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Book.class));

        return mapBookToBookDto(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByPublicationDate(LocalDate publicationDate) {
        log.debug("Get books by publication date: {}", publicationDate);

        return bookRepository.findByPublicationDate(publicationDate)
                .stream().map(this::mapBookToBookDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByAuthor(long authorId, int page, int size) {
        log.debug("Get books by author id: {}. Page - {}, number of records per page - {}", authorId, page, size);

        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException(authorId, Author.class));
        Pageable pageRequest = PageRequest.of(page, size);

        return bookRepository.findByAuthor(author, pageRequest)
                .stream().map(this::mapBookToBookDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByName(String name, int page, int size) {
        log.debug("Get books by name: {}. Page - {}, number of records per page - {}", name, page, size);
        Pageable pageRequest = PageRequest.of(page, size);

        return bookRepository.findByNameContainingIgnoreCase(name, pageRequest)
                .stream().map(this::mapBookToBookDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAll(int page, int size) {
        log.debug("Get list of books. Page - {}, number of records per page - {}", page, size);
        Pageable sortedByName = PageRequest.of(page, size, Sort.by("name").ascending());

        return bookRepository.findAll(sortedByName)
                .stream().map(this::mapBookToBookDto)
                .collect(Collectors.toList());
    }

    private BookDto mapBookToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }
}

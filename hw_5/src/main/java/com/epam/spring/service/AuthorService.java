package com.epam.spring.service;

import com.epam.spring.exception.EntityNotFoundException;
import com.epam.spring.model.dto.book.AuthorDto;
import com.epam.spring.model.entity.book.Author;
import com.epam.spring.persistence.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class AuthorService {
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;

    public AuthorDto saveAuthor(AuthorDto authorDto) {
        log.debug("Save author: {}", authorDto);

        Author author = modelMapper.map(authorDto, Author.class);
        authorRepository.save(author);

        return modelMapper.map(author, AuthorDto.class);
    }

    public void deleteAuthor(long id) {
        log.debug("Delete author by id {}", id);

        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Author.class));
        authorRepository.delete(author);
    }

    public AuthorDto updateAuthor(long id, AuthorDto authorDto) {
        log.debug("Update author with id {}: {}", id, authorDto);

        authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Author.class));

        Author author = modelMapper.map(authorDto, Author.class);
        author.setId(id);

        authorRepository.save(author);

        return mapAuthorToAuthorDto(author);
    }

    public AuthorDto getAuthorById(long id) {
        log.debug("Get author by id {}", id);

        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Author.class));

        return mapAuthorToAuthorDto(author);
    }

    public List<AuthorDto> getAuthor(LocalDate birthDate, String fullName) {
        log.debug("Get authors by birthDate - {} and full name - '{}'", birthDate, fullName);

        return authorRepository.findByBirthDateAndFullNameContainingIgnoreCase(birthDate, fullName)
                .stream().map(this::mapAuthorToAuthorDto)
                .collect(Collectors.toList());
    }

    public List<AuthorDto> getByFullName(String fullName) {
        log.debug("Get authors by full name: '{}'", fullName);

        return authorRepository.findByFullNameLike(fullName)
                .stream().map(this::mapAuthorToAuthorDto)
                .collect(Collectors.toList());
    }

    public List<AuthorDto> getByBirthDate(LocalDate birthDate) {
        log.debug("Get authors by birth date: {}", birthDate);

        return authorRepository.findByBirthDate(birthDate)
                .stream().map(this::mapAuthorToAuthorDto)
                .collect(Collectors.toList());
    }

    public List<AuthorDto> getAll() {
        log.debug("Get all authors");

        return authorRepository.findAll()
                .stream().map(this::mapAuthorToAuthorDto)
                .collect(Collectors.toList());
    }

    public AuthorDto mapAuthorToAuthorDto(Author author){
        return modelMapper.map(author, AuthorDto.class);
    }
}

package com.jonatan.libraryapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonatan.libraryapi.dto.author.AuthorRequestDto;
import com.jonatan.libraryapi.dto.author.AuthorResponseDto;
import com.jonatan.libraryapi.dto.book.BookResponseDto;
import com.jonatan.libraryapi.service.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto createdAuthor = authorService.createAuthor(authorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookResponseDto>> getBooksByAuthorId(@PathVariable Long authorId) {
        return ResponseEntity.ok(authorService.getBooksByAuthorId(authorId));
    }

    
    
}

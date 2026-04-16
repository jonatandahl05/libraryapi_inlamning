package com.jonatan.libraryapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonatan.libraryapi.dto.book.BookListV2ResponseDto;
import com.jonatan.libraryapi.service.BookService;

@RestController
@RequestMapping("/api/v2/books")
public class BookV2Controller {

    private final BookService bookService;

    public BookV2Controller(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public ResponseEntity<BookListV2ResponseDto> getAllBooksV2() {
        BookListV2ResponseDto response = bookService.getAllBooksV2();
        return ResponseEntity.ok(response);
    }
    
}

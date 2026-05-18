package com.jonatan.libraryapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jonatan.libraryapi.dto.book.BookListV2ResponseDto;
import com.jonatan.libraryapi.dto.book.BookRequestDto;
import com.jonatan.libraryapi.dto.book.BookResponseDto;
import com.jonatan.libraryapi.dto.book.BookV2ResponseDto;
import com.jonatan.libraryapi.entity.Author;
import com.jonatan.libraryapi.entity.Book;
import com.jonatan.libraryapi.exception.ResourceNotFoundException;
import com.jonatan.libraryapi.mapper.BookMapper;
import com.jonatan.libraryapi.repository.AuthorRepository;
import com.jonatan.libraryapi.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        Author author = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookRequestDto.getAuthorId()));

        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublicationYear(bookRequestDto.getPublicationYear());
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookResponseDto(savedBook);
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookResponseDto)
                .toList();
    }

    public BookResponseDto getBookById(Long id) {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
            return bookMapper.toBookResponseDto(book);
    }
    
    public void deleteBook(Long id) {
            if (!bookRepository.existsById(id)) {
                throw new ResourceNotFoundException("Book not found with id: " + id);
            }
            bookRepository.deleteById(id);
    }

    public BookListV2ResponseDto getAllBooksV2() {
        List<BookV2ResponseDto> books = bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookV2ResponseDto)
                .toList();
        return new BookListV2ResponseDto(books, "v2");
    }

    public Page<BookResponseDto> getAllBooksPaged(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookResponseDto);
    }




}

package com.jonatan.libraryapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jonatan.libraryapi.dto.author.AuthorRequestDto;
import com.jonatan.libraryapi.dto.author.AuthorResponseDto;
import com.jonatan.libraryapi.dto.book.BookResponseDto;
import com.jonatan.libraryapi.entity.Author;
import com.jonatan.libraryapi.exception.ResourceNotFoundException;
import com.jonatan.libraryapi.mapper.AuthorMapper;
import com.jonatan.libraryapi.mapper.BookMapper;
import com.jonatan.libraryapi.repository.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    public AuthorResponseDto createAuthor (AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());

        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toAuthorResponseDto(savedAuthor);
    }

    public AuthorResponseDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return authorMapper.toAuthorResponseDto(author);
    }
    
     public void deleteAuthor(Long id) {
            if (!authorRepository.existsById(id)) {
                throw new ResourceNotFoundException("Author not found with id: " + id);
            }
            authorRepository.deleteById(id);
    }

    public List<BookResponseDto> getBooksByAuthorId(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));
        return author.getBooks()
                .stream()
                .map(bookMapper::toBookResponseDto)
                .toList();
    }
    
}

package com.jonatan.libraryapi.mapper;

import org.springframework.stereotype.Component;

import com.jonatan.libraryapi.dto.book.BookResponseDto;
import com.jonatan.libraryapi.dto.book.BookV2ResponseDto;
import com.jonatan.libraryapi.entity.Book;

@Component
public class BookMapper {

    public BookResponseDto toBookResponseDto(Book book) {
        if (book == null) {
            return null;
        }

        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthorName(book.getAuthor() != null ? book.getAuthor().getName() : null);
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());

        return dto;
    }

    public BookV2ResponseDto toBookV2ResponseDto(Book book) {
        if (book == null) {
            return null;
        }

        BookV2ResponseDto dto = new BookV2ResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthorName(book.getAuthor() != null ? book.getAuthor().getName() : null);
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setAvailable(isBookAvailable(book));

        return dto;
    }

    private boolean isBookAvailable(Book book) {
        return book.getLoan() == null || book.getLoan().getReturnDate() != null;
    }
}

package com.jonatan.libraryapi.mapper;

import org.springframework.stereotype.Component;

import com.jonatan.libraryapi.dto.author.AuthorResponseDto;
import com.jonatan.libraryapi.entity.Author;

@Component
public class AuthorMapper {

    public AuthorResponseDto toAuthorResponseDto(Author author) {
        if (author == null) {
            return null;
        }

        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(author.getId());
        dto.setName(author.getName());

        return dto;
    }
    
}

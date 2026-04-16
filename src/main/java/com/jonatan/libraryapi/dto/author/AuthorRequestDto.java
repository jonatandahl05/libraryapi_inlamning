package com.jonatan.libraryapi.dto.author;

import jakarta.validation.constraints.NotBlank;

public class AuthorRequestDto {
    
    @NotBlank(message = "Author name is required")
    private String name;

    public AuthorRequestDto() {
    }

    public AuthorRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}

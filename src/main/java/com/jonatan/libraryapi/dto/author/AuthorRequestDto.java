package com.jonatan.libraryapi.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorRequestDto {

    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters")
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

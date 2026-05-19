package com.jonatan.libraryapi.dto.book;

import java.io.Serializable;

public class BookResponseDto implements Serializable {

    private Long id;
    private String title;
    private String authorName;
    private String isbn;
    private int publicationYear;

    public BookResponseDto() {
    }

    public BookResponseDto(Long id, String title, String authorName, String isbn, int publicationYear) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
    
}

package com.jonatan.libraryapi.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequestDto {

        @NotBlank(message = "Title is required")
        private String title;

        @NotNull(message = "Author ID is required")
        private Long authorId;

        private String isbn;

        private int publicationYear;

        public BookRequestDto() {
        }

        public BookRequestDto(String title, Long authorId, String isbn, int publicationYear) {
            this.title = title;
            this.authorId = authorId;
            this.isbn = isbn;
            this.publicationYear = publicationYear;
        }

        public String getTitle() {
            return title;
        }

        public Long getAuthorId() {
            return authorId;
        }

        public String getIsbn() {
            return isbn;
        }

        public int getPublicationYear() {
            return publicationYear;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthorId(Long authorId) {
            this.authorId = authorId;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public void setPublicationYear(int publicationYear) {
            this.publicationYear = publicationYear;
        }


    
}

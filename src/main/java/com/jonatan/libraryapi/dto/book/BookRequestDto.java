package com.jonatan.libraryapi.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookRequestDto {

        @NotBlank(message = "Title is required")
        @Size(min= 2, max = 100, message = "Title must be less than 100 characters")
        private String title;

        @NotNull(message = "Author ID is required")
        private Long authorId;

        @NotBlank(message = "ISBN is required")
        @Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
        private String isbn;

        @NotNull(message = "Publication year is required")
        @Min(value = 1450, message = "Publication year must be after 1450")
        private Integer publicationYear;

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

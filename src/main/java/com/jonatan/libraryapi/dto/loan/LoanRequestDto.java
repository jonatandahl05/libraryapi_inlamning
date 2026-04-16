package com.jonatan.libraryapi.dto.loan;

import jakarta.validation.constraints.NotNull;

public class LoanRequestDto {
    
    @NotNull(message = "Book ID is required")
    private Long bookId;

    public LoanRequestDto() {
    }

    public LoanRequestDto(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    
}

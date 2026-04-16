package com.jonatan.libraryapi.dto.loan;

import java.time.LocalDateTime;

public class LoanResponseDto {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public LoanResponseDto() {
    }

    public LoanResponseDto(Long id, Long bookId, String bookTitle, LocalDateTime loanDate, LocalDateTime returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    
    
    
}

package com.jonatan.libraryapi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false, unique = true)
    private Book book;
    
    @Column(nullable = false)
    private LocalDateTime loanDate;

    private LocalDateTime returnDate;

    public Loan() {
    }

    public Loan(Book book) {
        this.book = book;
    }
    
    @PrePersist
    public void prePersist(){
        if(loanDate == null){
            loanDate = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
            this.loanDate = loanDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
            this.returnDate = returnDate;
    }

    public void setBook(Book book) {
        this.book = book;
    }






    
}

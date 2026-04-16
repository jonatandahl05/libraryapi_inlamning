package com.jonatan.libraryapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "books")
public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, unique = true)
        private String isbn;
        private int publicationYear;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id", nullable = false)
        private Author author;

        @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
        private Loan loan;

        @Version
        private Long version;

        public Book() {
        }

        public Book(String title, String isbn, int publicationYear, Author author) {
            this.title = title;
            this.isbn = isbn;
            this.publicationYear = publicationYear;
            this.author = author;
        }

        public Long getId() {
            return id;
        }
    
        public String getTitle() {
            return title;
        }
    
        public String getIsbn() {
            return isbn;
        }
    
    
        public Author getAuthor() {
            return author;
        }
    
        public Loan getLoan() {
            return loan;
        }
    
        public Long getVersion() {
            return version;
        }

        public int getPublicationYear() {
            return publicationYear;
        } 
    
        public void setId(Long id) {
            this.id = id;
        }
    
        public void setTitle(String title) {
            this.title = title;
        }
    
        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
    
    
        public void setAuthor(Author author) {
            this.author = author;
        }
    
        public void setLoan(Loan loan) {
            this.loan = loan;
        }
    
        public void setVersion(Long version) {
            this.version = version;
        }

        public void setPublicationYear(int publicationYear) {
            this.publicationYear = publicationYear;
        }




    
}

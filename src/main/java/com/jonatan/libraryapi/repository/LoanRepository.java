package com.jonatan.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonatan.libraryapi.entity.Loan;

public interface  LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
    
}

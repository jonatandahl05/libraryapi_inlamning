package com.jonatan.libraryapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jonatan.libraryapi.dto.loan.LoanRequestDto;
import com.jonatan.libraryapi.dto.loan.LoanResponseDto;
import com.jonatan.libraryapi.entity.Book;
import com.jonatan.libraryapi.entity.Loan;
import com.jonatan.libraryapi.exception.BadRequestException;
import com.jonatan.libraryapi.exception.ResourceNotFoundException;
import com.jonatan.libraryapi.mapper.LoanMapper;
import com.jonatan.libraryapi.repository.BookRepository;
import com.jonatan.libraryapi.repository.LoanRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    private final Object loanLock = new Object();

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public LoanResponseDto createLoan(LoanRequestDto loanRequestDto) {
        synchronized (loanLock) {
            Book book = bookRepository.findById(loanRequestDto.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Book not found with id: " + loanRequestDto.getBookId()));

            boolean alreadyLoaned = loanRepository.existsByBookIdAndReturnDateIsNull(book.getId());

            if (alreadyLoaned) {
                throw new BadRequestException("Book with id " + book.getId() + " is already loaned out.");
            }

            Loan loan = new Loan();
            loan.setBook(book);
            loan.setLoanDate(LocalDateTime.now());
            loan.setReturnDate(null);

            Loan savedLoan = loanRepository.saveAndFlush(loan);

            return loanMapper.toLoanResponseDto(savedLoan);
        }
    }

    public List<LoanResponseDto> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toLoanResponseDto)
                .toList();
    }
}
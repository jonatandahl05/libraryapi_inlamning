package com.jonatan.libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonatan.libraryapi.dto.loan.LoanRequestDto;
import com.jonatan.libraryapi.dto.loan.LoanResponseDto;
import com.jonatan.libraryapi.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
            this.loanService = loanService;
    }
     
    @PostMapping
    public ResponseEntity<LoanResponseDto> createLoan (@Valid @RequestBody LoanRequestDto loanRequestDto) {
        LoanResponseDto createdLoan = loanService.createLoan(loanRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    @GetMapping
    public ResponseEntity<?> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    
}

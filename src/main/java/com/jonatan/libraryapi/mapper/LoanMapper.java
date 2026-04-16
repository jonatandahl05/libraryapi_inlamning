package com.jonatan.libraryapi.mapper;

import org.springframework.stereotype.Component;

import com.jonatan.libraryapi.dto.loan.LoanResponseDto;
import com.jonatan.libraryapi.entity.Loan;

@Component
public class LoanMapper {

    public LoanResponseDto toLoanResponseDto (Loan loan) {
        if (loan == null) {
            return null;
        }

        LoanResponseDto dto = new LoanResponseDto();
        dto.setId(loan.getId());
        dto.setBookId(loan.getBook().getId());
        dto.setBookTitle(loan.getBook().getTitle());
        dto.setLoanDate(loan.getLoanDate());
        dto.setReturnDate(loan.getReturnDate());

        return dto;
    }
        


    
}

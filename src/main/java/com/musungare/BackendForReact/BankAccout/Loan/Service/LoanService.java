package com.musungare.BackendForReact.BankAccout.Loan.Service;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import com.musungare.BackendForReact.BankAccout.Loan.Repository.LoanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private final LoanRepository loanRepository;


    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public ResponseEntity<String> saveApplication(Loan loan) {
        return ResponseEntity.ok().body(loanRepository.save(loan).toString());
    }
}

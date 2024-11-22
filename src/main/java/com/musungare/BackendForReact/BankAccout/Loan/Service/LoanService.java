package com.musungare.BackendForReact.BankAccout.Loan.Service;

import com.musungare.BackendForReact.BankAccout.Loan.Loan;
import com.musungare.BackendForReact.BankAccout.Loan.LoanStatus;
import com.musungare.BackendForReact.BankAccout.Loan.Repository.LoanRepository;
import com.musungare.BackendForReact.Utilities.LoanHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;


    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public ResponseEntity<String> saveApplication(Loan loan) {
        //logic for loans using helper classes

        loan.setLoanDate(LocalDate.now());
       double rate= LoanHelper.getInterestRate(loan.getPaybackPeriod(), loan.getLoanAmount(), loan.getLoanType());
        loan.setInterestRate(rate);
       double installment = LoanHelper.calculateMonthlyInstallment(loan.getLoanAmount(), rate, loan.getPaybackPeriod());
       loan.setMonthlyInstallment(installment);
        loan.setLoanStatus(LoanStatus.PENDING);

        return ResponseEntity.ok().body(loanRepository.save(loan).toString());
    }

    public ResponseEntity<List<Loan>> getLoanDetails(String email) {
       return ResponseEntity.ok().body(loanRepository.findByEmail(email));
    }
}
